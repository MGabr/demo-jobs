import React, { PureComponent } from "react";
import Card from "react-bootstrap/Card";
import PropTypes from "prop-types";
import Accordion from "react-bootstrap/Accordion";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import moment from "moment";
import DateTime from "react-datetime";
import "react-datetime/css/react-datetime.css"
import validate from "validate.js";

const dateFormat = "MMM yyyy";

validate.extend(validate.validators.datetime, {
    parse: function(value, options) {
        return moment(value, dateFormat);
    },
    format: function(value, options) {
        return moment(value).format(dateFormat);
    }
});

const constraints = {
    degree: { presence: {allowEmpty: false} },
    institution: { presence: {allowEmpty: false} },
    from: {
        datetime: {
            latest: moment(),
            message: "has to be before now"
        }
    },
    to: {
        datetime: {
            latest: moment(),
            message: "has to be before now"
        }
    }
};

const isValidDate = (currentDate, date) => date.isBefore(moment());

const educationValidator = (data) => validate(data, constraints, { fullMessages: false });

class Education extends PureComponent {

    state = {
        edit: this.props.edit
    };

    onChange = (event) => {
        const eChanged = { ...this.props.education };
        eChanged[event.target.name] = event.target.value;
        this.props.onChange(eChanged);
    };

    onDateFieldChange = (field) => (date) => {
        const eChanged = { ...this.props.education };
        eChanged[field] = date;
        this.props.onChange(eChanged);
    };

    onRemove = (event) => {
        event.preventDefault();
        this.props.onRemove();
    };

    onStartEdit = () => {
        this.setState({ edit: true });
    };

    onFinishEdit = (event) => {
        event.preventDefault();
        const validation = educationValidator(this.props.education);
        if (!validation) {
            this.setState({ edit: false });
        }
        this.props.onValidationChange(validation);
    };

    renderEdit = () => {
        const { validation, education } = this.props;
        return (
            <Form noValidate onSubmit={this.onFinishEdit}>
                <Card className="mb-3">
                    <Card.Header>
                        <Form.Row>
                            <Form.Group as={Col}>
                                <Form.Label className="ml-1">Degree</Form.Label>
                                <Form.Control name="position"
                                              value={education.degree}
                                              isInvalid={validation?.degree}
                                              onChange={this.onChange}
                                              placeholder="Your degree"/>
                                <Form.Control.Feedback type="invalid">{validation?.degree}</Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group as={Col}>
                                <Form.Label className="ml-1">Institution</Form.Label>
                                <Form.Control name="company"
                                              value={education.institution}
                                              isInvalid={validation?.institution}
                                              onChange={this.onChange}
                                              placeholder="Institution"/>
                                <Form.Control.Feedback type="invalid">{validation?.institution}</Form.Control.Feedback>
                            </Form.Group>
                        </Form.Row>
                        <Form.Row className="w-50">
                            <Form.Group as={Col}>
                                <Form.Label className="ml-1">From</Form.Label>
                                <DateTime name="from"
                                          value={education.from}
                                          className={validation?.from ? "is-invalid" : ""}
                                          onChange={this.onDateFieldChange("from")}
                                          inputProps={{
                                              placeholder: "From date",
                                              className: validation?.to ? "form-control is-invalid" : "form-control"
                                          }}
                                          dateFormat={dateFormat}
                                          isValidDate={isValidDate}
                                          viewMode="months"
                                          closeOnSelect="true"/>
                                <Form.Control.Feedback type="invalid">{validation?.from}</Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group as={Col} className="pr-0">
                                <Form.Label className="ml-1">To</Form.Label>
                                <DateTime name="to"
                                          value={education.to}
                                          className={validation?.to ? "is-invalid" : ""}
                                          onChange={this.onDateFieldChange("to")}
                                          inputProps={{
                                              placeholder: "To date",
                                              className: validation?.to ? "form-control is-invalid" : "form-control"
                                          }}
                                          dateFormat={dateFormat}
                                          isValidDate={isValidDate}
                                          viewMode="months"
                                          closeOnSelect="true"/>
                                <Form.Control.Feedback type="invalid">{validation?.to}</Form.Control.Feedback>
                            </Form.Group>
                        </Form.Row>
                        <Form.Group>
                            <Form.Label className="ml-1">Description</Form.Label>
                            <Form.Control name="description"
                                          value={education.description}
                                          onChange={this.onChange}
                                          placeholder="Describe your degree"
                                          as="textarea" rows="7"
                                          className="text-muted form-control-sm"/>
                        </Form.Group>
                        <Button variant="link" onClick={this.onFinishEdit}>Finish editing</Button>
                        <Button variant="link" onClick={this.onRemove}>Remove</Button>
                    </Card.Header>
                </Card>
            </Form>
        )
    };

    renderView = () => {
        const { id, degree, institution, from, to, description } = this.props.education;
        // only add accordion collapse if there is a description
        let collapseCursor;
        let collapse;
        if (description) {
            collapseCursor = "cursor-pointer";
            collapse =
                <Accordion.Collapse eventKey={"education-" + id}>
                    <Card.Body>
                        {
                            description.split("\n").map((d, idx) => {
                                return <Card.Text key={idx} className="text-muted small">{d}</Card.Text>
                            })
                        }
                    </Card.Body>
                </Accordion.Collapse>
        } else {
            collapseCursor = "";
            collapse = <React.Fragment/>
        }

        return (
            <Accordion>
                <Card className="mb-3">
                    <Accordion.Toggle as={Card.Header} eventKey={id} className={collapseCursor}>
                        <div className="float-md-left">
                            <Card.Title>{degree}</Card.Title>
                            <Card.Subtitle className="text-muted mb-2">
                                {institution}, {moment(from).format(dateFormat)} - {moment(to).format(dateFormat)}
                            </Card.Subtitle>
                        </div>
                        <div className="float-md-right">
                            <Card.Link href="#" onClick={this.onStartEdit}>Edit</Card.Link>
                            <Card.Link href="#" onClick={this.onRemove}>Remove</Card.Link>
                        </div>
                    </Accordion.Toggle>
                    {collapse}
                </Card>
            </Accordion>
        )
    };

    render() {
        if (this.state.edit) {
            return this.renderEdit();
        } else {
            return this.renderView();
        }
    }
}

Education.propTypes = {
    edit: PropTypes.bool,
    education: PropTypes.object.isRequired,
    onChange: PropTypes.func.isRequired,
    onRemove: PropTypes.func.isRequired,
    validation: PropTypes.object,
    onValidationChange: PropTypes.func.isRequired
};

export { Education, educationValidator};
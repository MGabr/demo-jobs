import React, { PureComponent} from "react";
import { Education, educationValidator } from "./Education";
import Button from "react-bootstrap/Button";
import PropTypes from "prop-types";

const educationListValidator = (data) => {
    return Object.fromEntries(Object.keys(data).map(idx => [idx, educationValidator(data[idx])]));
};

class EducationList extends PureComponent {

    onAdd = () => {
        const eAdd = {
            position: "",
            company: "",
            from: "",
            to: "",
            description: "",
            edit: true
        };
        this.props.onChange([ ...this.props.education, eAdd ]);
    };

    onFieldChange = (idx) => (value) => {
        const esChanged = [ ...this.props.education ];
        esChanged[idx] = value;
        this.props.onChange(esChanged);
    };

    onFieldRemove = (idx) => () => {
        const esChanged = [ ...this.props.education ];
        esChanged.splice(idx, 1);
        this.props.onChange(esChanged);
    };

    onValidationFieldChange = (field) => (value) => {
        const vChanged = { ...this.props.validation };
        vChanged[field] = value;
        this.props.onValidationChange(vChanged);
    };

    render() {
        const { education, validation } = this.props;
        return (
            <div className="mb-5">
                <h1 className="ml-1 h3 mb-4">Education</h1>
                {
                    education.map((e, idx) => {
                        return <Education key={idx}
                                               edit={e.edit}
                                               education={e}
                                               onChange={this.onFieldChange(idx)}
                                               onRemove={this.onFieldRemove(idx)}
                                               validation={validation?.[idx]}
                                               onValidationChange={this.onValidationFieldChange(idx)}/>
                    })
                }
                <Button variant="outline-primary" onClick={this.onAdd}>Add Education</Button>
            </div>
        )
    }
}

EducationList.propTypes = {
    education: PropTypes.array.isRequired,
    onChange: PropTypes.func.isRequired,
    validation: PropTypes.object,
    onValidationChange: PropTypes.func.isRequired
};

export { EducationList, educationListValidator };
import React, {Component} from "react";
import { WorkExperienceList, workExperienceListValidator } from "./WorkExperienceList";
import validate from "validate.js";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import SkillList from "./SkillList";
import axios from "axios";
import { EducationList, educationListValidator } from "./EducationList";

const constraints = {
    name: { presence: {allowEmpty: false} }
};

const talentValidator = (data) => {
    const validation = validate(data, constraints, { fullMessages: false });
    return {
        ...validation,
        workExperience: workExperienceListValidator(data.workExperience),
        education: educationListValidator(data.education)
    }
};

const deepUndefinedSearch = (obj) => {
    if (obj instanceof Object && obj.constructor === Object) {
        return Object.keys(obj).map((key) =>
            obj[key] instanceof Array
                ? obj[key].map((o) => deepUndefinedSearch(o)).filter((o) => !o).length === 0
                : deepUndefinedSearch(obj[key])
        ).filter((o) => !o).length === 0
    } else {
        return obj === undefined;
    }
};

const isValidated = (validation) => deepUndefinedSearch(validation);

const config = {
    withCredentials: true,
    headers: { "X-Requested-With": "XMLHttpRequest", }
};

class Talent extends Component {
    state = {
        talent: {
            name: "",
            skills: [],
            workExperience: [],
            education: []
        },
        validation: undefined
    };

    componentDidMount() {
        axios.get("/candidates/me", config)
            .then((response) => {
                console.log(response);
                this.setState({ talent: response.data });
            })
            .catch(() => {
                // TODO: error message from server
            });
    }

    onChange = (event) => {
        const tChanged = { ...this.state.talent };
        tChanged[event.target.name] = event.target.value;
        this.setState({ talent: tChanged });
    };

    onFieldChange = (field) => (value) => {
        const tChanged = { ...this.state.talent };
        tChanged[field] = value;
        this.setState({ talent: tChanged });
    };

    onValidationFieldChange = (field) => (value) => {
        const vChanged = { ...this.state.validation };
        vChanged[field] = value;
        this.setState({ validation: vChanged });
    };

    onSubmit = (event) => {
        const validation = talentValidator(this.state.talent);
        console.log(validation);
        this.setState({ validation: { ...validation } });
        event.preventDefault();
        event.stopPropagation();

        if (isValidated(validation)) {
            axios.put("/candidates/me", this.state.talent, config)
                .catch(() => {
                    // TODO: error message from server
                });
        }
    };

    render() {
        const { talent, validation } = this.state;
        return (
            <Form noValidate onSubmit={this.onSubmit}>
                <div className="mb-5">
                    <h1 className="ml-1 h3 mt-4 mb-4">My Profile</h1>
                    <Form.Group>
                        <Form.Label className="ml-1">Name</Form.Label>
                        <Form.Control name="name"
                                      value={talent.name}
                                      isInvalid={validation?.name}
                                      onChange={this.onChange}
                                      placeholder="Your name"/>
                        <Form.Control.Feedback type="invalid">{validation?.name}</Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label className="ml-1">Skills</Form.Label>
                        <SkillList skills={talent.skills}
                                   onChange={this.onFieldChange("skills")}
                                   editable={true}/>
                    </Form.Group>
                </div>

                <WorkExperienceList workExperience={talent.workExperience}
                                    onChange={this.onFieldChange("workExperience")}
                                    validation={validation?.workExperience}
                                    onValidationChange={this.onValidationFieldChange("workExperience")}/>

                <EducationList education={talent.education}
                               onChange={this.onFieldChange("education")}
                               onValidationChange={this.onValidationFieldChange("education")} />

                <Button type="submit">Save profile</Button>
            </Form>
        )
    }
}

export default Talent;
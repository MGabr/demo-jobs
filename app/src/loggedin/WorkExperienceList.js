import React, { PureComponent} from "react";
import { WorkExperience, workExperienceValidator } from "./WorkExperience";
import Button from "react-bootstrap/Button";
import PropTypes from "prop-types";

const workExperienceListValidator = (data) => {
    return { ...Object.keys(data).map(idx => [idx, workExperienceValidator(data[idx])]) };
};

class WorkExperienceList extends PureComponent {

    onAdd = () => {
        const eAdd = {
            position: "",
            company: "",
            from: "",
            to: "",
            description: "",
            edit: true
        };
        this.props.onChange([ ...this.props.workExperience, eAdd ]);
    };

    onFieldChange = (idx) => (value) => {
        const esChanged = [ ...this.props.workExperience ];
        esChanged[idx] = value;
        this.props.onChange(esChanged);
    };

    onFieldRemove = (idx) => () => {
        const esChanged = [ ...this.props.workExperience ];
        esChanged.splice(idx, 1);
        this.props.onChange(esChanged);
    };

    onValidationFieldChange = (field) => (value) => {
        const vChanged = { ...this.props.validation };
        vChanged[field] = value;
        this.props.onValidationChange(vChanged);
    };

    render() {
        const { workExperience, validation } = this.props;
        return (
            <div className="mb-5">
                <h1 className="ml-1 h3 mb-4">Work Experience</h1>
                {
                    workExperience.map((e, idx) => {
                        return <WorkExperience key={idx}
                                               edit={e.edit}
                                               workExperience={e}
                                               onChange={this.onFieldChange(idx)}
                                               onRemove={this.onFieldRemove(idx)}
                                               validation={validation?.[idx]}
                                               onValidationChange={this.onValidationFieldChange(idx)}/>
                    })
                }
                <Button variant="outline-primary" onClick={this.onAdd}>Add Work Experience</Button>
            </div>
        )
    }
}

WorkExperienceList.propTypes = {
    workExperience: PropTypes.array.isRequired,
    onChange: PropTypes.func.isRequired,
    validation: PropTypes.object,
    onValidationChange: PropTypes.func.isRequired
};

export { WorkExperienceList, workExperienceListValidator };
import React, { PureComponent } from "react";
import Card from "react-bootstrap/Card";
import SkillList from "./SkillList";

class Job extends PureComponent {

    onFieldChange = (field) => (value) => {
        const jChanged = { ...this.props.job };
        jChanged[field] = value;
        // this.props.onChange(jChanged);
    };

    render() {
        const { companyName, name, skills } = this.props.job;
        return (
            <Card className="mb-3">
                <Card.Body>
                    <Card.Title>{name}</Card.Title>
                    <Card.Subtitle className="text-muted">At {companyName}</Card.Subtitle>
                </Card.Body>
                <Card.Footer>
                    <SkillList skills={skills} onChange={this.onFieldChange("skills")} size="sm"/>
                </Card.Footer>
            </Card>
        )
    }
}

export default Job;
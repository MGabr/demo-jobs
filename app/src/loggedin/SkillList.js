import React, { PureComponent } from "react";
import Skill from "./Skill";
import PropTypes from "prop-types";
import "./skill.scss"

class SkillList extends PureComponent {

    onKeyPress = (event) => {
        const keyCode = event.keyCode || event.which;
        if (keyCode === 13) {
            event.preventDefault();
            const sAdd = event.target.textContent;
            if (sAdd) {
                this.props.onChange([ ...this.props.skills, sAdd ]);
            }
            event.target.textContent = "";
        }
    };

    onFieldRemove = (idx) => () => {
        const ssChanged = [ ...this.props.skills ];
        ssChanged.splice(idx, 1);
        this.props.onChange(ssChanged);
    };

    render() {
        let skillInput;
        if (this.props.editable) {
            skillInput =  <div className="btn btn-outline-primary skill-input"
                               placeholder="Add Skill..."
                               onKeyPress={this.onKeyPress}
                               contentEditable />
        } else {
            skillInput = <React.Fragment/>
        }

        return (
            <div className={this.props.size === "sm" ? "mb-0" : "mb-3"}>
                {
                    this.props.skills.map((s, idx) => {
                        return <Skill key={idx}
                                      skill={s}
                                      onRemove={this.onFieldRemove(idx)}
                                      editable={this.props.editable}
                                      size={this.props.size}/>
                    })
                }
                {skillInput}
            </div>
        )
    }
}

SkillList.propTypes = {
    skills: PropTypes.array.isRequired,
    onChange: PropTypes.func,
    editable: PropTypes.bool,
    size: PropTypes.string
};

export default SkillList;
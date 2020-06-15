import React, { PureComponent } from "react";
import PropTypes from "prop-types";
import Alert from "react-bootstrap/Alert";
import "./skill.scss"

const hashCode = (str) => {
    let hash = 0;
    if (str.length === 0) {
        return hash;
    }
    for (let i = 0; i < str.length; i++) {
        let char = str.charCodeAt(i);
        hash = ((hash << 5) - hash) + char;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};

const mod = (n, m) => {
    return ((n % m) + m) % m;
};

class Skill extends PureComponent {

    render() {
        const variants = ["primary"];
        const variant = variants[mod(hashCode(this.props.skill), variants.length)];
        return (
            <Alert variant={variant}
                   className={(this.props.size === "sm" ? "skill-sm" : "skill")  + " mr-2 text-white"}
                   onClose={this.props.onRemove}
                   dismissible={this.props.editable}>{this.props.skill}</Alert>
        )
    }
}

Skill.propTypes = {
    skill: PropTypes.string.isRequired,
    onRemove: PropTypes.func,
    editable: PropTypes.bool,
    size: PropTypes.string
};

export default Skill;
import React from "react";
import validate from "validate.js";

const userContext = React.createContext({user: {}});

const constraints = {
    email: {
        email: {
            message: "Email has to be a valid email"
        }
    },
    password: {
        presence: true,
        length: {
            minimum: 6,
            message: "Password must be at least 6 characters"
        }
    }
};

const userValidator = (data) => validate(data, constraints, { fullMessages: false });

export { userContext, userValidator };
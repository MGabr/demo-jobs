import React, {PureComponent} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./login.css";
import { userContext, userValidator } from "../userContext";

class Login extends PureComponent {

    state = {
        user: {
            email: "",
            password: ""
        },
        validation: undefined
    };

    onChange = (event) => {
        event.persist();
        this.setState((prevState) => {
            const uChanged = { ...prevState.user };
            uChanged[event.target.name] = event.target.value;
            return { user: uChanged  }
        });
    };

    onSubmit = (login) => (event) => {
        event.preventDefault();
        const validation = userValidator(this.state.user);
        this.setState({ validation: validation });
        if (!validation) {
            login(this.state.user, () => {
                // TODO: error message from server
            });
        }
    };

    render() {
        const { user, validation } = this.state;
        return (
            <div className="text-center" style={{maxWidth: "20em", margin: "auto"}}>
                <h1 className="h3 mb-3 font-weight-normal">
                    Please log in
                </h1>
                <Form className="mb-3">
                    <Form.Group>
                        <Form.Control placeholder="Your email address"
                                      value={user.email}
                                      isInvalid={validation?.email}
                                      type="email"
                                      name="email"
                                      onChange={this.onChange}
                                      className="no-round-b"/>
                        <Form.Control placeholder="Your password"
                                      value={user.password}
                                      isInvalid={validation?.password}
                                      type="password"
                                      name="password"
                                      onChange={this.onChange}
                                      className="no-round-t"/>
                        <Form.Control.Feedback type="invalid">{validation?.email}</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">{validation?.password}</Form.Control.Feedback>
                    </Form.Group>
                    <userContext.Consumer>
                        {({ login }) => (
                            <Button variant="primary"
                                    type="submit"
                                    className="btn-block"
                                    onClick={this.onSubmit(login)}>
                                Login
                            </Button>
                        )}
                    </userContext.Consumer>
                </Form>
            </div>
        )
    }
}

export default Login;
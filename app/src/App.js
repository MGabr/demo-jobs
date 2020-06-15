import React, { Component } from "react";

import "./App.scss";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import Cover from "./cover/Cover";
import LoggedIn from "./loggedin/LoggedIn";
import { userContext } from "./userContext";
import axios from "axios";

const config = {
    withCredentials: true,
    headers: { "X-Requested-With": "XMLHttpRequest", }
};

class App extends Component {

    state = {
        user: undefined
    };

    register = (user, onError) => {
        axios.post("/users", user, config)
            .then((response) => {
                this.setState({ user: { ...user, id: response.data.id } });
            })
            .catch(onError);
    };

    authenticate = (user, onError) => {
        let userConfig = user ? { ...config, auth: { username: user.email, password: user.password } } : config;
        axios.get("/users/me", userConfig)
            .then((response) => {
                this.setState({ user: response.data });
            })
            .catch((error) => {
                this.setState({ user: undefined });
                onError(error);
            });
    };

    logout = () => {
        axios.post("/logout", config).finally(() => {
            this.setState({ user: undefined })
        })
    };

    componentDidMount() {
        this.authenticate(this.state.user, () => { });
    }

    render() {
        const value = {
            user: this.state.user,
            register: this.register,
            login: this.authenticate,
            logout: this.logout
        };
        return (
            <userContext.Provider value={value}>
                <Router>
                    <userContext.Consumer>
                        {({ user }) => (
                            user
                                ?
                                <Route path="/" component={LoggedIn}/>
                                :
                                <Switch>
                                    <Route path="/cover" component={Cover}/>
                                    <Route render={() => <Redirect to="/cover/login" />} />
                                </Switch>
                        )}
                    </userContext.Consumer>

                </Router>
            </userContext.Provider>

        );
    }

}

export default App;

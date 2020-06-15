import React, { Component } from "react";

import "./App.scss";
import { BrowserRouter as Router } from "react-router-dom";
import { userContext } from "../userContext";
import axios from "axios";
import Header from "./Header";
import Main from "./Main";
import Footer from "./Footer";

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
            <div style={this.state.user ? {} : coverRootStyle}>
                <div style={this.state.user ? {} : coverContainerStyle}
                     className={this.state.user ? "" : containerClasses + " text-center"}>
                    <userContext.Provider value={value}>
                        <Router>
                            <Header style={this.state.user ? loggedInHeaderStyle : {}}
                                    className={this.state.user ? containerClasses : ""}/>
                            <Main className={this.state.user ? containerClasses : "cover"}/>
                            <Footer/>
                        </Router>
                    </userContext.Provider>
                </div>
            </div>
        );
    }
}

const coverRootStyle = {
    height: "100%",
    width: "100%",
    display: "flex",
    backgroundColor: "#333",
    color: "#fff",
    textShadow: "0 .05rem .1rem rgba(0, 0, 0, .5)",
    boxShadow: "inset 0 0 5rem rgba(0, 0, 0, .5)"
};

const loggedInHeaderStyle = {
    backgroundColor: "#333",
    color: "#fff",
    textShadow: "0 .05rem .1rem rgba(0, 0, 0, .5)",
    boxShadow: "inset 0 0 5rem rgba(0, 0, 0, .5)"
};

const coverContainerStyle = {
    maxWidth: "42em"
};

const containerClasses = "d-flex w-100 p-3 mx-auto flex-column";

export default App;

import React, { Component } from "react";
import { NavLink, Route, Redirect, Switch } from "react-router-dom";
import Talent from "./Talent";
import { userContext } from "../userContext";

class LoggedIn extends Component {

    onLogout = (logout) => (event) => {
        event.preventDefault();
        logout();
    };

    render() {
        return (
            <div>
                <header className="masthead mb-auto" style={headerRoot}>
                    <div className="d-flex w-100 p-3 mx-auto flex-column"
                         style={loggedInContainer}>
                        <div>
                            <h3 className="masthead-brand">DemoJobs</h3>
                            <nav className="nav nav-masthead justify-content-center">
                                <NavLink to="/jobs" className="nav-link">Jobs</NavLink>
                                <NavLink to="/applications" className="nav-link">My Applications</NavLink>
                                <NavLink to="/profile" className="nav-link">My Profile</NavLink>
                                <userContext.Consumer>
                                    {({ logout }) => (
                                        <NavLink to="/cover/login"
                                                 onClick={this.onLogout(logout)}
                                                 className="nav-link">Logout</NavLink>
                                    )}
                                </userContext.Consumer>
                            </nav>
                        </div>
                    </div>
                </header>
                <main role="main">
                    <div className="d-flex w-100 p-3 mx-auto flex-column"
                         style={loggedInContainer}>
                        <Switch>
                            <Route exact path="/profile" component={Talent}/>
                            <Route path="/" render={() => <Redirect to="/profile" />} />
                        </Switch>
                    </div>
                </main>
            </div>
        );
    }
}

const headerRoot = {
    backgroundColor: "#333",
    color: "#fff",
    textShadow: "0 .05rem .1rem rgba(0, 0, 0, .5)",
    boxShadow: "inset 0 0 5rem rgba(0, 0, 0, .5)"
};

const loggedInContainer = {
    maxWidth: "42em"
};

export default LoggedIn;
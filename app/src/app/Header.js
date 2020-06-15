import React, {Component} from "react";
import { NavLink } from "react-router-dom";
import { userContext } from "../userContext";

export default class Header extends Component {

    onLogout = (logout) => (event) => {
        event.preventDefault();
        logout();
    };

    render() {
        return (
            <React.Fragment>
                <header className="masthead mb-auto" style={this.props.style}>
                    <div className={this.props.className} style={{maxWidth: "42em"}}>
                        <div className="inner">
                            <h3 className="masthead-brand">DemoJobs</h3>
                            <nav className="nav nav-masthead justify-content-center">
                                <userContext.Consumer>
                                    {({ user, logout }) => (
                                        user ?
                                            <React.Fragment>
                                                <NavLink to="/jobs" className="nav-link">Jobs</NavLink>
                                                <NavLink to="/applications" className="nav-link">My Applications</NavLink>
                                                <NavLink to="/profile" className="nav-link">My Profile</NavLink>
                                                <NavLink to="/cover/login"
                                                         onClick={this.onLogout(logout)}
                                                         className="nav-link">Logout</NavLink>
                                            </React.Fragment>
                                            :
                                            <React.Fragment>
                                                <NavLink to="/cover/talents" className="nav-link">For Talents</NavLink>
                                                <NavLink to="/cover/companies" className="nav-link">For Companies</NavLink>
                                                <NavLink to="/cover/login" className="nav-link">Login</NavLink>
                                            </React.Fragment>
                                    )}
                                </userContext.Consumer>
                            </nav>
                        </div>
                    </div>
                </header>
            </React.Fragment>
        )
    }
}
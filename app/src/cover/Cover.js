import React from "react";
import { NavLink, Route } from "react-router-dom";

import CoverTalents from "./CoverTalents";
import CoverCompanies from "./CoverCompanies";
import Login from "./Login";
import Registration from "./Registration";

function Cover() {
    return (
            <div style={coverRoot}>
                <div className="text-center d-flex w-100 h-100 p-3 mx-auto flex-column"
                     style={coverContainer}>
                    <header className="masthead mb-auto">
                        <div className="inner">
                            <h3 className="masthead-brand">DemoJobs</h3>
                            <nav className="nav nav-masthead justify-content-center">
                                <NavLink to="/cover/talents" className="nav-link">For Talents</NavLink>
                                <NavLink to="/cover/companies" className="nav-link">For Companies</NavLink>
                                <NavLink to="/cover/login" className="nav-link">Login</NavLink>

                    </nav>
                        </div>
                    </header>
                    <main role="main" className="inner cover">
                        <Route exact path="/cover/talents" component={CoverTalents}/>
                        <Route exact path="/cover/companies" component={CoverCompanies}/>
                        <Route exact path="/cover/login" component={Login}/>
                        <Route exact path="/cover/talents/register" component={Registration}/>
                        <Route exact path="/cover/companies/register" component={Registration}/>
                    </main>
                    <footer className="mastfoot mt-auto">
                        <div className="inner">
                            <p>DemoJobs by <a href="https://github.com/MGabr">MGabr</a></p>
                        </div>
                    </footer>
                </div>
            </div>
    );
}

const coverRoot = {
    height: "100%",
    width: "100%",
    display: "flex",
    backgroundColor: "#333",
    color: "#fff",
    textShadow: "0 .05rem .1rem rgba(0, 0, 0, .5)",
    boxShadow: "inset 0 0 5rem rgba(0, 0, 0, .5)"
};

const coverContainer = {
    maxWidth: "42em"
};

export default Cover;
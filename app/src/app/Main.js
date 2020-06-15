import React from "react";
import CoverTalents from "../cover/CoverTalents";
import CoverCompanies from "../cover/CoverCompanies";
import Login from "../cover/Login";
import Registration from "../cover/Registration";
import Talent from "../loggedin/Talent";
import { userContext } from "../userContext";
import { Route, Switch, Redirect } from "react-router-dom";

export default function Main(props) {
    return (
        <main role="main" className={"inner " + props.className} style={{maxWidth: "42em"}}>
            <Switch>
                <userContext.Consumer>
                    {({ user }) => (
                        user ?
                            <React.Fragment>
                                <Route exact path="/profile" component={Talent}/>
                                <Route path="/" render={() => <Redirect to="/profile" />} />
                            </React.Fragment>
                            :
                            <React.Fragment>
                                <Route exact path="/cover/talents" component={CoverTalents}/>
                                <Route exact path="/cover/companies" component={CoverCompanies}/>
                                <Route exact path="/cover/login" component={Login}/>
                                <Route exact path="/cover/talents/register" component={Registration}/>
                                <Route exact path="/cover/companies/register" component={Registration}/>
                                <Route path="/" render={() => <Redirect to="/cover/talents" />} />
                            </React.Fragment>
                    )}
                </userContext.Consumer>
            </Switch>
        </main>
    )
}
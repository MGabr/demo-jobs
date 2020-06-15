import React from "react";
import {userContext} from "../userContext";

export default function Footer() {
    return (
        <userContext.Consumer>
            {({ user }) => (
                user ?
                    <React.Fragment />
                    :
                        <footer className="mastfoot mt-auto">
                            <div style={{maxWidth: "42em"}}>
                                <div className="inner">
                                <p>DemoJobs by <a href="https://github.com/MGabr">MGabr</a></p>
                                </div>
                            </div>
                        </footer>

            )}
        </userContext.Consumer>
    )
}
import React from "react";
import Button from "react-bootstrap/Button";
import { LinkContainer } from "react-router-bootstrap";

function CoverTalents() {
    return (
        <React.Fragment>
            <h1 className="cover-heading">Looking for the perfect job?</h1>
            <p className="lead">
                DemoJobs is the easy way to find a job.<br />
                With more than 100 companies offering more than 1000 jobs.
            </p>
            <p className="lead">
                <LinkContainer to="/cover/talents/register">
                    <Button variant="primary" size="lg" className="mr-3">Find a job!</Button>
                </LinkContainer>
                <LinkContainer to="/cover/companies">
                    <Button variant="secondary" size="sm">Offering jobs?</Button>
                </LinkContainer>
            </p>
        </React.Fragment>
    )
}

export default CoverTalents;
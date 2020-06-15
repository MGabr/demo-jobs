import React from "react";
import { LinkContainer } from "react-router-bootstrap";
import Button from "react-bootstrap/Button";

function CoverCompanies() {
    return (
        <main role="main" className="inner cover">
            <h1 className="cover-heading">Looking for top talents?</h1>
            <p className="lead">
                DemoJobs is the easy way to find talents.<br />
                With more than 10.000 talents currently looking for jobs.<br />
                Fill vacant jobs fast and easily!
            </p>
            <p className="lead">
                <LinkContainer to="/cover/companies/register">
                    <Button variant="primary" size="lg" className="mr-3">Find talents!</Button>
                </LinkContainer>
                <LinkContainer to="/cover/talents">
                    <Button variant="secondary" size="sm">Looking for jobs?</Button>
                </LinkContainer>
            </p>
        </main>
    )
}

export default CoverCompanies;
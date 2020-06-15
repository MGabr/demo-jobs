import React, {Component} from 'react';
import Job from "./Job";

class Jobs extends Component {

    state = {
        jobs: []
    };

    render() {
        return (
            this.state.jobs.map((j, idx) => {
                return <Job key={idx} job={j}/>
            })
        )
    }
}

export default Jobs;
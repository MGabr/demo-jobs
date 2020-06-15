import React, {Component} from 'react';
import Job from "./Job";

class Jobs extends Component {

    state = {
        jobs: [
            {
                id: "1",
                companyId: "2",
                companyName: "Iteratec",
                name: "Junior Software Engineer",
                description: "",
                skills: [
                    "Java",
                    "Spring",
                    "MongoDB",
                    "Javascript",
                    "React"
                ]
            },
            {
                id: "2",
                companyId: "2",
                companyName: "Iteratec",
                name: "Junior Software Engineer",
                description: "",
                skills: [
                    "Java",
                    "Spring",
                    "MongoDB",
                    "Javascript",
                    "React"
                ]
            },
            {
                id: "3",
                companyId: "2",
                companyName: "Iteratec",
                name: "Junior Software Engineer",
                description: "",
                skills: [
                    "Java",
                    "Spring",
                    "MongoDB",
                    "Javascript",
                    "React"
                ]
            }
        ]
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
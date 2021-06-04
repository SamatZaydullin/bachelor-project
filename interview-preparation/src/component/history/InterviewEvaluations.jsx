import React, {Component} from 'react'
import AuthenticationService from "../../service/AuthenticationService";
import axios from "axios";
import Header from "../Header";
import Navbar from "../Navbar";
import InterviewBar from "./InterviewBar";

class InterviewEvaluations extends Component {

    constructor(props) {
        super(props)

        this.state = {
            interviews: []
        }
        this.getInterviews = this.getInterviews.bind(this);
        this.getInterviews();
    }

    getInterviews(){
        AuthenticationService.setupAxiosInterceptors();

        axios.get("http://localhost:8080/api/interviews")
            .then(res => {
                console.log(res.data)
                this.setState({
                    interviews: res.data,
                });
            }).catch((error) => {
            console.log("error")
        });
    }

    render() {
        return (
            <div>
                <Header/>
                <Navbar/>
                <div className={'container'}>

                    <div class="section">
                        <div class="container">
                            <div id="store" class="col-md-12">

                                {this.state.interviews.map(interview => <InterviewBar interview={interview}/>)}

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default InterviewEvaluations
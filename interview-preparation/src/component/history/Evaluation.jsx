import React, {Component} from 'react'
import EvaluationBar from "./EvaluationBar";
import AuthenticationService from "../../service/AuthenticationService";
import axios from "axios";
import Header from "../Header";
import Navbar from "../Navbar";

class Evaluation extends Component {

    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id==null ? "" : this.props.match.params.id,
            evaluations: []
        }
        this.getEvaluations = this.getEvaluations.bind(this);
        this.getEvaluations();
    }

    getEvaluations(){
        AuthenticationService.setupAxiosInterceptors();

        axios.get("http://localhost:8080/api/evaluations/" + this.state.id)
            .then(res => {
                console.log(res.data)
                this.setState({
                    evaluations: res.data,
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

                                {this.state.evaluations.map(evaluation => <EvaluationBar evaluation={evaluation}/>)}

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Evaluation
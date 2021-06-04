import React, {Component} from 'react'
import Navbar from "./Navbar";
import AuthenticationService from "../service/AuthenticationService";
import axios from "axios";
import Header from "./Header";
import LinkBar from "./lessions/LinkBar";

class MainPage extends Component {

    constructor(props) {
        super(props)

        this.state = {
            questions:[]
        }
        this.getQuestions = this.getQuestions.bind(this);
        this.getQuestions()
    }
    getQuestions(){
        AuthenticationService.setupAxiosInterceptors();

        axios.get("http://localhost:8080/api/questions/l")
            .then(res => {
                console.log(res.data)
                this.setState({
                    questions: res.data
                })
            }).catch((error) => {
                console.log(error)
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

                            {this.state.questions.map(question => <LinkBar question={question}/>)}

                        </div>
                    </div>
                </div>
            </div>
            </div>
    )
    }
}

export default MainPage
import React, {Component} from 'react'
import Navbar from "../Navbar";
import axios from "axios";
import AuthenticationService from "../../service/AuthenticationService";
import Header from "../Header";
import 'react-dropdown/style.css';

const API_URL = 'http://localhost:8080/api';

class QuestionAdd extends Component {

    constructor(props) {
        super(props)

        const id = this.props.match.params.id

        this.state = {
            id:this.props.match.params.id,
            specialities: [],
            specialityId:'',
            theme:'',
            title:'',
            price:'',
            question: '',
                ans1:'',
                ans2:'',
                ans3:'',
                ans4:'',
                ans5:'',
            owner: '',
            state: '',
            category:'',
            complexity:'',
            options:[],
            answerItems:[10]
        }
        this.getAvailableSpecialities()
        this.handleChange = this.handleChange.bind(this);
        this.addQuestion = this.addQuestion.bind(this);

    }

    getAvailableSpecialities(){
        axios.get("http://localhost:8080/api/specialities")
            .then(value => {
                this.setState({
                    specialities: value.data
                })
            })
            .catch(reason => console.log(reason))
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        )
    }


    addQuestion() {
        let answers = [
            this.state.ans1,
            this.state.ans2,
            this.state.ans3,
            this.state.ans4,
            this.state.ans5
        ]

        AuthenticationService.setupAxiosInterceptors()
        AuthenticationService.addQuestion(
            this.state.specialityId,
            this.state.theme,
            this.state.question,
            answers,
            this.state.complexity
        )
            .then((res)=>{
                this.setState(
                    {
                        question:'',
                        ans1:'',
                        ans2:'',
                        ans3:'',
                        ans4:'',
                        ans5:''
                    }
                )
                console.log(res)
            }
        )
    }

    render() {
        return (
            <div>

                <Header/>
                <Navbar/>
                <div className={'container'}>


                <div className="section">
                    <div className="container">
                        <div className="row">

                            <div className="col-md-12">
                                <div className="product-details">

                                    <ul className="product-links">
                                        <li>??????????????????????????:</li>
                                        {
                                            this.state.specialities.map((speciality)=>
                                                <li><input className="product-category" type="radio" name="specialityId" id={speciality.id}
                                                           value={speciality.id} onChange={this.handleChange} />
                                                    <label htmlFor={speciality.id}>{speciality.title}</label>
                                                </li>

                                            )
                                        }


                                    </ul>

                                    <div>
                                        <h2 className="product-details">
                                            <div className="form-group">
                                                <input className="input" type="text" name="theme" placeholder="????????"
                                                       value={this.state.theme} onChange={this.handleChange} required/>
                                            </div>
                                        </h2>
                                    </div>
                                    <div>
                                        <h3 className="product-name">
                                            <div className="form-group">
                                                <input className="input" type="textarea" name="question" placeholder="????????????"
                                                       value={this.state.question} onChange={this.handleChange} required/>
                                            </div>
                                        </h3>
                                        <span className="product-available">
                                            {this.state.state}
                                        </span>
                                    </div>


                                    <p>
                                        <textarea className="input" type="textarea" name="ans1" placeholder="??????????"
                                               value={this.state.ans1} onChange={this.handleChange} required style={{width: "700px", height: "100px"}}/>
                                    </p>
                                    <p>
                                        <textarea className="input" type="textarea" name="ans2" placeholder="??????????"
                                               value={this.state.ans2} onChange={this.handleChange} style={{width: "700px", height: "100px"}}/>
                                    </p>
                                    <p>
                                        <textarea className="input" type="textarea" name="ans3" placeholder="??????????"
                                               value={this.state.ans3} onChange={this.handleChange} style={{width: "700px", height: "100px"}}/>
                                    </p>
                                    <p>
                                        <textarea className="input" type="textarea" name="ans4" placeholder="??????????"
                                               value={this.state.ans4} onChange={this.handleChange} style={{width: "700px", height: "100px"}}/>
                                    </p>
                                    <p>
                                        <textarea className="input" type="textarea" name="ans5" placeholder="??????????"
                                               value={this.state.ans5} onChange={this.handleChange} style={{width: "700px", height: "100px"}}/>
                                    </p>

                                    <ul className="product-links">
                                        <li>??????????????????:</li>
                                        <li><input className="product-category" type="radio" name="complexity" id="category1"
                                                   value="EASY" onChange={this.handleChange} />
                                               <label htmlFor="category1">????????????</label>
                                        </li>
                                        <li><input className="product-category" type="radio" name="complexity" id="category2"
                                                   value="MIDDLE" onChange={this.handleChange} />
                                            <label htmlFor="category2">??????????????</label>
                                        </li>
                                        <li><input className="product-category" type="radio" name="complexity" id="category3"
                                                   value="HARD" onChange={this.handleChange} />
                                            <label htmlFor="category3">??????????????</label>
                                        </li>
                                        <li><input className="product-category" type="radio" name="complexity" id="category4"
                                                   value="VERY_HARD" onChange={this.handleChange} />
                                            <label htmlFor="category4">?????????? ??????????????</label>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div className="col-md-3 col-md-push-1">
                                <button className="primary-btn order-submit"
                                        onClick={this.addQuestion}>????????????????
                                </button>
                            </div>

                        </div>
                    </div>
                </div>
                </div>
            </div>
        )
    }
}

export default QuestionAdd
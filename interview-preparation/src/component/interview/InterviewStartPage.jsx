import React, {Component} from 'react'
import Header from "../Header";
import Navbar from "../Navbar";
import axios from "axios";
import AuthenticationService from "../../service/AuthenticationService";

class InterviewStartPage extends Component {

    constructor(props) {
        super(props)

        this.state = {
            questionsCount: '',
            speciality: '',
            specialityId: '',
            specialities:[],
            themeIds: [],
            themes: []
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSpecialityChange = this.handleSpecialityChange.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
        this.getThemesBySpeciality = this.getThemesBySpeciality.bind(this);
        this.getSpecialities = this.getSpecialities.bind(this);
        this.startInterview = this.startInterview.bind(this);
        this.getSpecialities();
    }

    handleChange(event) {
        this.setState({
                [event.target.name]: event.target.value
            }
        )
    }

    handleSpecialityChange(event) {
        this.setState({
                specialityId: event.target.value
            }
        )
        console.log(event.target.value)
        this.getThemesBySpeciality(event.target.value)
    }

    handleCheckboxChange(event) {
        const input = event.target.value;
        const isChecked = event.target.checked;
        this.setState(
            {
                themeIds: this.state.themeIds.includes(input) && !isChecked
                    ? this.state.themeIds.filter((item) => item !== input)
                    : [...this.state.themeIds, input]
            },
            () => {
                console.log(this.state.themeIds);
                console.log(this.state.questionsCount);
            }
        );
    }


    getSpecialities(){
        AuthenticationService.setupAxiosInterceptors();

        axios.get("http://localhost:8080/api/specialities")
            .then(res => {
                this.setState({
                    specialities: res.data,
                })
            }).catch((error) => {
            console.log("error")
        });
    }

    getThemesBySpeciality(specialityId){
        AuthenticationService.setupAxiosInterceptors();

        axios.get("http://localhost:8080/api/themes/speciality/" + specialityId)
            .then(res => {
                this.setState({
                    themes: res.data,
                });
                this.state.themeIds=[]
            }).catch((error) => {
            console.log("error")
        });
    }

    startInterview(){

        AuthenticationService.startInterview(
            this.state.questionsCount,
            this.state.themeIds
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

                            <div id="store" className="col-md-5">
                                <img src={"img/demand-g.png"} width={"200px"}/>

                            </div>
                            <div id="store" className="col-md-7">

                                <div className="form-group">
                                    <input className="input" type="number" name="questionsCount" placeholder="Количество вопросов"
                                           value={this.state.questionsCount} onChange={this.handleChange} required/>
                                </div>
                                <ul className="product-links">
                                    <li>Специальность:</li>
                                    {
                                        this.state.specialities.map((speciality)=>
                                            <li><input className="product-category" type="radio" name="specialityId" id={speciality.title}
                                                       value={speciality.id} onChange={this.handleSpecialityChange} />
                                                <label htmlFor={speciality.title}>{speciality.title}</label>
                                            </li>

                                        )
                                    }

                                </ul>
                                <div>
                                    <div>

                                    </div>
                                    <ul className="product-links">
                                        <li>Темы:</li>
                                        {this.state.themes.map(theme =>
                                            <li><input className="product-category"
                                                       type="checkbox"
                                                       id={theme.id}
                                                       value={theme.id}
                                                       onChange={this.handleCheckboxChange}
                                                />
                                                <label htmlFor={theme.id}>{theme.title}</label>
                                            </li>
                                        )}
                                    </ul>
                                    <div id="store" className="col-md-7">
                                        <label>
                                            <button onClick={this.startInterview}>Пройти собеседование</button>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default InterviewStartPage
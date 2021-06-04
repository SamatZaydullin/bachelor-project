import React, {Component} from 'react'
import AuthenticationService from "../../service/AuthenticationService";
import Service from "../../service/Service";
import axios from "axios";
import Header from "../Header";

const API_URL = 'http://localhost:8080'
const API_IMG_URL = 'http://localhost:8080/image/'


class ProfileEdit extends Component {

    constructor(props) {
        super(props)

        this.state = {
            password: '',
            repassword: '',
            firstName: '',
            lastName: '',
            address: '',
            phone: '',
            image: API_IMG_URL+'default.png',
            file: null
        }

        this.handleChange = this.handleChange.bind(this)
        this.updateProfile = this.updateProfile.bind(this)
        this.goBack = this.goBack.bind(this)
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.getUserData();

    }

    getUserData(){
        AuthenticationService.setupAxiosInterceptors();
        axios.get(`${API_URL}/user-info`)
            .then(res=>{
                    console.log(res)
                    const user = res.data
                    this.setState({
                        login: user.login,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        address: user.address,
                        phone: user.phone
                    })
                    if (user.image){
                        console.log(user.image)
                        this.setState({
                            image: API_IMG_URL + user.image
                        })
                    }
                }
            )
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        )
    }

    onFormSubmit(e){
        e.preventDefault();

        let data = new FormData();
        data.append('file', e.target.files[0]);


        axios.post("http://localhost:8080/image-upload", data)
            .then((response) => {
                console.log(response)
                this.setState({
                    image: API_IMG_URL + response.data
                });
            }).catch((error) => {
        });
    }
    onChange(e) {
        this.setState({file:e.target.files[0]});
    }

    goBack(){
        this.props.history.push(`/user/${localStorage.getItem("userId")}`);
    }

    updateProfile() {
        if (this.state.password===this.state.repassword){
            AuthenticationService
                .updateUser(
                    this.state.password,
                        this.state.firstName,
                        this.state.lastName,
                        this.state.address,
                        this.state.phone
                ).then((response) => {
                    this.props.history.push(`/user/${localStorage.getItem("userId")}`)
                }).catch(() => {
                }
            )
        }else {
            let passwordState = document.getElementById("password-state");
            passwordState.innerText = "Пароли не совпадают!!!";
            passwordState.style.color = "red";

        }

    }

    render() {
        return (

            <div>
                <Header/>
                <div className={'container'}>
                <div className="section">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-4">
                                <div id="product-main-img">

                                    <div className="profile-container">
                                        <img src={this.state.image} alt="ава"/>
                                        <span className="settings">

                                            {/*<form onChange={this.onFormSubmit}>*/}
                                            <label htmlFor="myImage">
                                            <i className="fa fa-upload">
                                                <input type="file" id="myImage" onChange= {this.onFormSubmit} hidden/>
                                                    </i>
                                                </label>
                                                {/*<button type="submit">Upload</button>*/}
                                            {/*</form>*/}

                                            <button href="#" style={{border: "none", marginLeft: "40%"}}>
                                                <i className="fa fa-trash"></i>Удалить</button>
                                        </span>
                                    </div>

                                </div>
                            </div>

                            <div className="col-md-offset-4">
                                <div className="product-details">
                                    <h2 className="product-name">{this.state.firstName} {this.state.lastName}</h2>
                                    <div className="billing-details">
                                        <div className="section-title">
                                            <h3 className="title">Изменение профиля</h3>
                                        </div>
                                        <div className="section-title">
                                            <h4 id="password-state"></h4>
                                        </div>
                                        <div className="form-group">
                                            <input className="input" type="text" name="firstName" placeholder="Имя"
                                                   value={this.state.firstName} onChange={this.handleChange} required/>
                                        </div>
                                        <div className="form-group">
                                            <input className="input" type="text" name="lastName" placeholder="Фамилия"
                                                   value={this.state.lastName} onChange={this.handleChange} required/>
                                        </div>
                                        <div className="form-group">
                                            <input className="input" type="password" name="password" placeholder="Пароль"
                                                value={this.state.password} onChange={this.handleChange}/>
                                        </div>
                                        <div className="form-group">
                                            <input className="input" type="password" name="repassword"
                                                placeholder="Подтвердите пароль" value={this.state.repassword}
                                                onChange={this.handleChange}/>
                                        </div>
                                        <div className="form-group">
                                            <input className="input" type="number" name="phone" placeholder="Телефон"
                                                   value={this.state.phone} onChange={this.handleChange} required/>
                                        </div>
                                        <div className="form-group">
                                            <input className="input" type="text" name="address" placeholder="Адрес"
                                                   value={this.state.address} onChange={this.handleChange}/>
                                        </div>
                                    </div>
                                    <button className="primary-btn order-submit"
                                            onClick={this.updateProfile}>Обновить
                                    </button>

                                    <button className="primary-btn order-submit"
                                            onClick={this.goBack}>Назад
                                    </button>
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
export default ProfileEdit
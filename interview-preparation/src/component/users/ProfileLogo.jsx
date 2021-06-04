import React, {Component} from 'react'
import AuthenticationService from "../../service/AuthenticationService";
import axios from "axios";

const API_URL = 'http://localhost:8080/api'

class ProfileLogo extends Component {
    constructor(props) {
        super(props)

        this.state = {
            href: "/user/profile",
            firstName: '',
            lastName: '',
        }
        this.getUserData = this.getUserData.bind(this)
        this.logout = this.logout.bind(this)
        this.getUserData(localStorage.getItem("userId"));
    }

    logout(){
        AuthenticationService.logout()
    }

    getUserData(id){
        if (AuthenticationService.isUserLoggedIn()){
            AuthenticationService.setupAxiosInterceptors();
            axios.get(`${API_URL}/users/profile`)
                .then(res=>{
                        const user = res.data
                        this.setState({
                            firstName: user.firstName,
                            lastName: user.lastName,
                        })
                    }
                )
        }
    }


    render() {
        if (!AuthenticationService.isUserLoggedIn()) {
            return (
                <ul className="header-links pull-right">
                    <li><a href="/register"><i className="fa fa-user-circle"></i> Регистрация</a></li>
                    <li><a href="/login"><i className="fa fa-user-o"></i> Вход</a></li>
                </ul>
            )
        } else {
            return (
                <ul className="header-links pull-right">
                    <li><a href={this.state.href}><i className="fa fa-user-o"></i>{this.state.firstName} {this.state.lastName}</a></li>
                    <li><a href='/' onClick={this.logout}><i className="fa fa-sign-out"></i>Выйти</a></li>
                </ul>
            )
        }

    }
}

export default ProfileLogo
import React, {Component} from 'react'
import AuthenticationService from "../service/AuthenticationService";
import axios from "axios";

const API_URL = 'http://localhost:8080/api';

class HeaderButtons extends Component {
    constructor(props) {
        super(props)

        this.state = {
            href: "/user/" + localStorage.getItem("userId"),
            firstName: '',
            lastName: '',
        }
        this.getUserData(localStorage.getItem("userId"));
        this.logout = this.logout.bind(this)
    }

    logout(){
        AuthenticationService.logout()
    }

    getUserData(){
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
                <div>

                    <div className="col-md-6">
                        <div className="header-search">
                            <form>
                                <input className="input input-select" placeholder="Поиск..." value=""/>
                                <button className="search-btn">Поиск</button>
                            </form>
                        </div>
                    </div>

                    <div className="col-md-3 clearfix">
                        <div className="header-ctn">

                            <div>
                                <a href={""} target="_blank">
                                    <i className="fa fa-instagram" aria-hidden="true"></i>
                                    <span>Следите</span>
                                </a>
                            </div>

                            <div>
                                <a href={""} target="_blank">
                                    <i className="fa fa-vk" aria-hidden="true"></i>
                                    <span>за нами!</span>
                                </a>
                            </div>

                        </div>
                    </div>
                </div>
            )
        } else {
            return (

                <div>

                    <div className="col-md-5">
                        <div className="header-search">
                            <form>
                                <input className="input input-select" placeholder="Поиск..."/>
                                <button className="search-btn">Поиск</button>
                            </form>
                        </div>
                    </div>

                    <div className="col-md-4 clearfix">
                        <div className="header-ctn">

                            <div>
                                <a href="/question/add">
                                    <i className="fa fa-plus-square"></i>
                                    <span>Добавить</span>
                                </a>
                            </div>

                            <div className="dropdown">
                                <a href="/evaluations/">
                                    <i className="fa fa-bookmark-o"></i>
                                    <span>Оценки</span>
                                </a>
                            </div>

                            <div>
                                <a href="/interview">
                                    <i className="fa fa-comments"></i>
                                    <span>Интервью</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            )
        }

    }
}

export default HeaderButtons
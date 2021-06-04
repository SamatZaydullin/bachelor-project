import React, {Component} from 'react'
import AuthenticationService from "../../service/AuthenticationService";
import axios from "axios";
import ProfileBtn from "./ProfileBtn";
import Header from "../Header";

const API_URL = 'http://localhost:8080/api'
const API_IMG_URL = 'http://localhost:8080/image/'

class Profile extends Component {

    constructor(props) {
        super(props)

        const id = this.props.match.params.id

        this.state = {
            id:this.props.match.params.id,
            login: '',
            firstName: '',
            lastName: '',
            address: '',
            phone: '',
            image: '',
            products:[]
        }

        this.getUserData();
    }

    getUserData(){
        AuthenticationService.setupAxiosInterceptors();

        axios.get(`${API_URL}/users/profile`)
            .then(res=>{
                    console.log(res)
                    const user = res.data
                    this.setState({
                        login: user.login,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        address: user.address,
                        phone: user.phone,
                    })
                if (user.image){
                    console.log(user.image)
                    this.setState({
                        image: API_IMG_URL + user.image
                    })
                }else {
                    this.setState({
                        image: '/img/default.png'
                    })
                }
                }
            ).catch(()=>{
            this.props.history.push(`/rents`)
        })
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

                                    <div className="product-preview">
                                        <img src={this.state.image} alt="ава"/>
                                    </div>

                                </div>
                            </div>

                            <div className="col-md-offset-4">
                                <div className="product-details">
                                    <h2 className="profile-name">{this.state.firstName} {this.state.lastName}</h2>
                                    <h4 className="profile-name">{this.state.login}</h4>
                                    <h4 className="profile-name">{this.state.phone}</h4>
                                    <h4 className="profile-name">{this.state.address}</h4>

                                    <ProfileBtn state={this.state.id}/>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="section">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12">
                                <div className="section-title text-center">
                                    <h3 className="title">Изучаемые темы:</h3>
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
export default Profile
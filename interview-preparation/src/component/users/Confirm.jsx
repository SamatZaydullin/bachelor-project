import React, {Component} from 'react'
import {notify} from 'react-notify-toast'
import Redirect from "react-router-dom/es/Redirect";
import Header from "../Header";
import Navbar from "../Navbar";

const API_URL = 'http://localhost:8080/api';

class Confirm extends Component {

    state = {
        confirming: true
    }

    componentDidMount = () => {
        const { id } = this.props.match.params

        fetch(`${API_URL}/confirm/${id}`)
            .then(data => {
                this.setState({ confirming: false })
                notify.show(data.msg)
            })
            .catch(err => console.log(err))
    }

    render = () =>
        <div>
            <Header/>
            <Navbar/>
            <div className={'container'}>
                <div className='confirm'>
                    {this.state.confirming
                        ? <p>Подтверждение email...</p>
                        : <Redirect to="/login"/>
                    }
                </div>
            </div>
        </div>
}
export default Confirm
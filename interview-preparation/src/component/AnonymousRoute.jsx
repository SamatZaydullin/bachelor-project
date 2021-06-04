import React, {Component} from 'react'
import {Route} from 'react-router-dom'
import AuthenticationService from '../service/AuthenticationService';
import Redirect from "react-router-dom/es/Redirect";

class AnonymousRoute extends Component {
    render() {
        if (AuthenticationService.isUserLoggedIn()) {
            return <Redirect to={"/"}/>
        } else {
            return <Route {...this.props} />
        }

    }
}

export default AnonymousRoute
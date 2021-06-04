import React, {Component} from 'react'
import {Route} from 'react-router-dom'
import AuthenticationService from '../service/AuthenticationService';
import Redirect from "react-router-dom/es/Redirect";

class AuthenticatedRoute extends Component {
    render() {
        if (AuthenticationService.isUserLoggedIn()) {
            return <Route {...this.props} />
        } else {
            return <Redirect to="/login" />
        }

    }
}

export default AuthenticatedRoute
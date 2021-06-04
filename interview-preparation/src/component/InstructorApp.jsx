import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Login from './users/Login';
import Registration from './users/Registration';
import MainPage from "./MainPage";
import Profile from "./users/Profile";
import NoMatch from "./NoMatch";
import Footer from "./Footer";
import SuccessfulRegistration from "./users/SuccessfulRegistration";
import Confirm from "./users/Confirm";
import AuthenticatedRoute from "./AuthenticatedRoute";
import QuestionAdd from "./lessions/QuestionAdd";
import AnonymousRoute from "./AnonymousRoute";
import Interview from "./interview/Interview";
import InterviewStartPage from "./interview/InterviewStartPage";
import Evaluation from "./history/Evaluation";
import QuestionPage from "./lessions/QuestionPage";
import InterviewEvaluations from "./history/InterviewEvaluations";

class InstructorApp extends Component {


    render() {
        return (
            <>
                    <Router>
                        <>
                            <Switch>
                                <Route path="/" exact component={MainPage} />
                                <AnonymousRoute path="/login" exact component={Login} />
                                <AnonymousRoute path="/register" exact component={Registration} />
                                <AnonymousRoute path="/successful" exact component={SuccessfulRegistration}/>
                                <Route exact path='/confirm/:id' component={Confirm} />
                                <Route path="/question/add" exact component={QuestionAdd}/>
                                <Route exact path="/question/:id" component={QuestionPage}/>
                                <AuthenticatedRoute exact path='/user/profile' component={Profile}/>
                                <AuthenticatedRoute exact path='/interview' component={InterviewStartPage}/>
                                <AuthenticatedRoute exact path="/interview/:id" component={Interview} />
                                <AuthenticatedRoute exact path="/evaluations" component={InterviewEvaluations} />
                                <AuthenticatedRoute exact path="/evaluations/:id" component={Evaluation} />

                                <Route component={NoMatch} />
                            </Switch>
                        </>
                    </Router>
                <Footer/>
            </>
        )
    }
}

export default InstructorApp
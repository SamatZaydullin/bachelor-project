import axios from 'axios'
import jwt_decode from "jwt-decode";

const API_URL = 'http://localhost:8080/api'
const tokenName = 'Authorization'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'


class AuthenticationService {

    executeJwtLogin(email, password) {
        return axios.post(`${API_URL}/auth/login`, {
            email,
            password
        })
    }

    executeJwtRegister(email, password, firstName, lastName) {
        console.log(email);
        return axios.post(`${API_URL}/auth/registration`, {
            email,
            password,
            firstName,
            lastName
        })
    }

    updateUser(password, firstName, lastName, address, phone){
        this.setupAxiosInterceptors();
        return axios.put(`${API_URL}/update`, {
            password,
            firstName,
            lastName,
            address,
            phone
        })
    }

    addQuestion(specialityId, theme, text, answers, complexity){
        return axios.post(`${API_URL}/questions`, {
            specialityId, theme, text, answers, complexity})
    }

    registerSuccessfulLoginForJwt(token) {
        console.log(token)
        let decoded = jwt_decode(token);
        console.log(decoded.sub);
        let username = decoded.sub
        localStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        localStorage.setItem(tokenName, token)
    }

    logout() {
        localStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        localStorage.removeItem(tokenName);
        localStorage.removeItem('userId');
    }

    isUserLoggedIn(){
        let user = localStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        console.log(user)
        return user !== null;

    }

    getUserData(){
        this.setupAxiosInterceptors();
        axios.get(`${API_URL}/users/profile`)
            .then(res=>{
                    return res.data
                }
            )
    }

    startInterview(questionsCount, themes){
        this.setupAxiosInterceptors();
        axios.post("http://localhost:8080/api/interviews", {questionsCount, themes})
            .then(res => {
                window.location.assign("/interview/" + res.data)
            }).catch((error) => {
            console.log("error")
        });
    }

    checkAndGetNext(id, answerText, questionId){
        this.setupAxiosInterceptors();
        return axios.put("http://localhost:8080/api/interviews/" + id, {
            answerText, questionId
        });
    }

    getContent(id){
        this.setupAxiosInterceptors();
        console.log("get contenttt")
        return axios.get("http://localhost:8080/api/interviews/" + id);
    }

    accept(rentId, status){
        this.setupAxiosInterceptors();
        const form = new FormData();
        form.append("status", status)
        return axios.put(`${API_URL}/feedback/${rentId}/accept`, form)
    }
    setupAxiosInterceptors() {
        axios.defaults.headers.common[tokenName] = "Bearer " + localStorage.getItem(tokenName);
    }
}

export default new AuthenticationService()
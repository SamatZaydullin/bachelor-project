import axios from 'axios'

const API_URL = 'http://localhost:8080/api'

class Service {

    getQuestion(id){
        return axios.get(`${API_URL}/questions/${id}`)
    }

}

export default new Service()
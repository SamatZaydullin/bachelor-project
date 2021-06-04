import React, {Component} from 'react'
import Navbar from "../Navbar";
import Service from "../../service/Service";
import Header from "../Header";

class QuestionPage extends Component {

    constructor(props) {
        super(props)

        const id = this.props.match.params.id

        this.state = {
            id:this.props.match.params.id,
            theme: '',
            text: '',
            complexity:'',
            answers:''

        }
        this.getQuestion = this.getQuestion(this)
        this.getQuestion(this.state.id)
    }

    getQuestion(id){
        Service.getQuestion(id)
            .then((res)=>{
                this.setState({
                    theme: res.data.theme,
                    text: res.data.text,
                    complexity: res.data.complexity,
                    answers: res.data.answers
                })

            }).catch(()=>{
            this.props.history.push(`/questions`)
        })
    }


    render() {
        return (
            <div>
                <Header/>
                <Navbar/>
                <div className={'container'}>

                    <div className="section">
                        <div className="container">
                            <div className="row">

                                <div className="col-md-5">
                                    <div className="product-details">
                                        <h2 className="product-name">{this.state.theme}</h2>
                                        Описание:
                                        <p>{this.state.text}</p>

                                        <ul className="product-links">
                                            <li>Сложность:</li>
                                            <li>{this.state.complexity}</li>
                                        </ul>
                                        <ul className="product-links">
                                            <li>Сложность:</li>
                                            <li>{this.state.answers}</li>
                                        </ul>
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

export default QuestionPage
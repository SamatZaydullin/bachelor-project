import React, {Component} from 'react'

class EvaluationBar extends Component {

    constructor(props) {
        super(props)

        console.log(props);

        this.state = {
            id: this.props.evaluation.id,
            theme: this.props.evaluation.theme,
            complexity: this.props.evaluation.complexity,
            questionText: this.props.evaluation.questionText,
            correctAnswer: this.props.evaluation.correctAnswer,
            userAnswer: this.props.evaluation.userAnswerText,
            answerScore: this.props.evaluation.answerScore

        }
    }

    render() {
        return (
            <div>
                <div className="col-md-11 col-xs-5">
                    <div className="product fav-product-body">
                        <div className="product-category" style={{'font-weight': 'bold'}}>Тема: {this.state.theme}</div>
                        <a href={"/question/" + this.state.id}>Подробнее: <div className="product-name"style={{color: 'blue'}}>{this.state.questionText}</div></a>
                        <div className="product-details" style={{color: 'green'}}>Правильный ответ: {this.state.correctAnswer}</div>
                        <div className="product-old-price">Текущий ответ: {this.state.userAnswer}</div>
                        <div className="product-rating" style={{color: 'orange'}}>Оценка: {this.state.answerScore}</div>

                    </div>
                </div>
            </div>
        )
    }
}

export default EvaluationBar
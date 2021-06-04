import React, {Component} from 'react'

class InterviewBar extends Component {

    constructor(props) {
        super(props)

        console.log(props);

        this.state = {
            id: this.props.interview.interviewUUID,
            questionCount: this.props.interview.questionCount,
            veryBadScoreCount: this.props.interview.veryBadScoreCount,
            badScoreCount: this.props.interview.badScoreCount,
            goodScoreCount: this.props.interview.goodScoreCount,
            excellentScoreCount: this.props.interview.excellentScoreCount,
            dateTime: this.props.interview.dateTime

        }
    }

    render() {
        return (
            <div>
                <div className="col-md-11 col-xs-5">
                    <div className="product fav-product-body">
                        <div className="product-category" style={{'font-weight': 'bold'}}>Количество вопросов: {this.state.questionCount}</div>
                        <a href={"/evaluations/" + this.state.id}>Подробнее: <div className="product-name"style={{color: 'blue'}}>{this.state.dateTime}</div></a>
                        <div className="product-details" style={{color: 'green'}}>Отлично: {this.state.excellentScoreCount}</div>
                        <div className="product-details" style={{color: '#908f00'}}>Хорошо: {this.state.goodScoreCount}</div>
                        <div className="product-details" style={{color: 'orange'}}>Плохо: {this.state.badScoreCount}</div>
                        <div className="product-details" style={{color: 'red'}}>Очень плохо: {this.state.veryBadScoreCount}</div>
                    </div>
                </div>
            </div>
        )
    }
}

export default InterviewBar
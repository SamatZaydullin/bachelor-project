import React, {Component} from 'react'

class LinkBar extends Component {

    constructor(props) {
        super(props)

        console.log(props);

        this.state = {
            link: '/questions/' + this.props.question.id,
            theme: this.props.question.themeTitle,
            text: this.props.question.text,
            complexity: this.props.question.complexity,
            answer: this.props.question.answer
        }
    }


    render() {
        return (
            <div>
                <div className="col-md-11 col-xs-5">
                    <div className="product">
                        <div className="product-details" style={{color: 'blue'}}><a href={this.state.link}>Подробнее...</a></div>
                        <div className="product-details" style={{color: 'black'}}>Тема: {this.state.theme}</div>
                        <div className="product-details" style={{color: 'red'}}>Вопрос: {this.state.text}</div>
                        <div className="product-details" style={{color: 'green'}}>Пример правильного ответа: {this.state.answer}</div>
                        <div className="product-details" style={{color: 'grey'}}>Сложность: {this.state.complexity}</div>
                    </div>
                </div>
            </div>
        )
    }
}

export default LinkBar
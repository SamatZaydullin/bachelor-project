import React, {Component} from 'react'

class AnswerAddBar extends Component {

    constructor(props) {
        super(props)

        this.state = {
            answer: '',
        }
        this.handleChange = this.handleChange.bind(this);

    }
    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        )
    }


    render() {
        return (
            <div>

                <p>
                    <input className="input" type="textarea" name="answer" placeholder="Ответ"
                           value={this.state.answer} onChange={this.handleChange} required/>
                </p>
            </div>
        )
    }
}

export default AnswerAddBar
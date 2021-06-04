import React, {Component} from 'react'

class SpecialityBtn extends Component {

    constructor(props) {
        super(props)
        console.log(this.props)
        this.state = {
            speciality: props.state.speciality

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

    render(){
        return (
            <div>
                <li><input className="product-category" type="radio" name="speciality" id={this.state.speciality.id}
                           value={this.state.speciality.title} onChange={this.handleChange} />
                    <label htmlFor={this.state.speciality.id}>{this.state.speciality.title}</label>
                </li>
            </div>
        )
    }
}

export default SpecialityBtn
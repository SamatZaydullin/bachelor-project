import React, {Component} from 'react'
import Header from "../Header";
import Navbar from "../Navbar";

class SuccessfulRegistration extends Component {

    render() {
        return (
            <div>
                <Header/>
                <Navbar/>
                <div className={'container'}>
                <div className="section">
                    <div className="container">
                        <div className="row">

                            <div className="col-md-7">
                                <div className="billing-details">
                                    <p>Для завершения регистрации вам потребуется пройти подтверждение.
                                        Мы выслали вам письмо со специальным кодом.</p>
                                    <p>Электронное письмо для подтверждения аккаунта содержит ссылку для подтверждения адреса электронной почты.
                                        Пройдите по ссылке, чтобы подтвердить регистрацию вашего аккаунта.</p>
                                    <p>Если вы не получили данное письмо, проверьте папки «Спам» и «Удаленные», так как письмо могло автоматически туда перейти.</p>

                                </div>
                                <a href="/login" className="primary-btn order-submit"
                                        >Вход
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>


        )
    }
}

export default SuccessfulRegistration
import React, {Component} from 'react'

class Footer extends Component {

    render() {
        return (
            <div>
                <footer id="footer">
                    <div className="section">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-3 col-xs-6">
                                    <div className="footer">
                                        <h3 className="footer-title">О нас</h3>
                                        <p>Подготовка к интервью</p>
                                        <ul className="footer-links">
                                            <li><a ><i className="fa fa-map-marker"></i>Казань, ул. Кремлевская, 35</a></li>
                                            <li><a ><i className="fa fa-phone"></i>+021-95-51-84</a></li>
                                            <li><a ><i className="fa fa-envelope-o"></i>email@email.com</a></li>
                                        </ul>
                                    </div>
                                </div>

                                <div className="col-md-3 col-xs-6">
                                    <div className="footer">
                                        <h3 className="footer-title">Категории</h3>
                                        <ul className="footer-links">
                                            <li><a >Java Core</a></li>
                                            <li><a >Collections</a></li>
                                            <li><a >Multithreading</a></li>
                                            <li><a >Spring</a></li>
                                            <li><a >Database</a></li>
                                        </ul>
                                    </div>
                                </div>

                                <div className="clearfix visible-xs"></div>

                                <div className="col-md-3 col-xs-6">
                                    <div className="footer">
                                        <h3 className="footer-title">Информация</h3>
                                        <ul className="footer-links">
                                            <li><a >О нас</a></li>
                                            <li><a >Политика конфидициальности</a></li>
                                            <li><a >Условия использования</a></li>
                                        </ul>
                                    </div>
                                </div>

                                <div className="col-md-3 col-xs-6">
                                    <div className="footer">
                                        <h3 className="footer-title">Услуги</h3>
                                        <ul className="footer-links">
                                            <li><a >Мой аккаунт</a></li>
                                            <li><a >Мои добавленные</a></li>
                                            <li><a >Подготовка</a></li>
                                            <li><a >Помощь</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </footer>
        
            </div>
        )
    }
}

export default Footer
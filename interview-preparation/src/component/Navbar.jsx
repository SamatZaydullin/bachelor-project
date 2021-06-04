import React, {Component} from 'react'

class Navbar extends Component {

    render() {
        return (
                <nav id="navigation">
                    <div className="container">
                        <div id="responsive-nav">
                            <ul className="main-nav nav navbar-nav">
                                <li className="active"><a href="/">Все темы</a></li>
                                <li><a href="#">ООП</a></li>
                                <li><a href="#">Java Core</a></li>
                                <li><a href="#">JCF</a></li>
                                <li><a href="#">Java 8</a></li>
                                <li><a href="#">Многопоточность</a></li>
                                <li><a href="#">Базы данных</a></li>
                                <li><a href="#">Шаблоны проектирования</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>
    )
    }
    }

export default Navbar
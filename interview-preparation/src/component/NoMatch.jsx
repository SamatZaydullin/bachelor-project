import React, {Component} from 'react';
import Header from "./Header";
import Navbar from "./Navbar";

class NoMatch extends Component {


    render() {
        return (

           <div>
               <Header/>
               <Navbar/>
               <div className={'container'}>
               <div id="breadcrumb" class="section">
                   <div class="container">
                       <div class="col-md-8">
                           <h1 class="breadcrumb-header">404</h1>
                           <h3 class="breadcrumb-header">Страница не найдена</h3>
                       </div>
                   </div>
               </div>
               </div>
           </div>
        )
    }
}

export default NoMatch
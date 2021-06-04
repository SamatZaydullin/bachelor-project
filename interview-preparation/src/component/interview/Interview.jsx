import React, {Component} from 'react'
import {ReactMic} from "@cleandersonlobo/react-mic";
import Header from "../Header";
import Navbar from "../Navbar";
import ReactAudioPlayer from 'react-audio-player';
import AuthenticationService from "../../service/AuthenticationService";

class Interview extends Component {

    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            record: false,
            isRecording: false,
            blobURL: '',
            isBlocked: false,
            recognized: '',
            questionId: '',
            audioUrl: '',
            questionText: '',
            questionCount: '',
            imgSrc: '../img/micro.png',
            buttonComment: 'Проверить и перейти к следующему вопросу',
            recognitionStatus: 'Здесь будет отображаться распознанная речь'
        }
        this.handleChange = this.handleChange.bind(this);
        this.onStop = this.onStop.bind(this);
        this.toRecord = this.toRecord.bind(this);
        this.audio = new Audio(this.state.url);
        this.getContent = this.getContent.bind(this);
        this.getContent();

        this.checkAndGetNext = this.checkAndGetNext.bind(this);
        console.log(this.state.id);
    }
    startRecording = () => {
        this.setState({
            record: true
        });
    }

    stopRecording = () => {
        this.setState({
            record: false
        });
    }

    onData(recordedBlob) {
        console.log('chunk of real-time data is: ', recordedBlob);
    }

    onStop(recordedBlob) {
        this.setState({
            recognitionStatus: 'Ждите, идет распозначание речи'
        })
        console.log('recordedBlob is: ', recordedBlob);
        var xhr=new XMLHttpRequest();
        var fd=new FormData();

        xhr.onload = () => {
            console.log(xhr)
            this.setState({
                recognized: xhr.responseText,
                recognitionStatus: 'Распознано:'
            })
        }

        fd.append("audio", recordedBlob.blob);
        xhr.open("POST","http://localhost:8080/api/audio/records",true);
        xhr.send(fd);
    }

    handleChange(event) {
        this.setState({recognized: event.target.value});
    }

    play = () => {
        this.audio.play();
    };

    getContent(){
        AuthenticationService.getContent(this.state.id)
            .then(value => {
                console.log(value.data)
                this.setState({
                    audioUrl: value.data.audioId,
                    questionText: value.data.questionText,
                    questionId: value.data.questionId,
                    questionsCount: value.data.questionCount
                })
            });
    }

    checkAndGetNext(){
        if (this.state.questionsCount > 0){
            AuthenticationService.checkAndGetNext(this.state.id, this.state.recognized, this.state.questionId)
                .then(value => {
                    console.log(value);
                })
                .catch(reason => console.log(reason))

            this.setState({
                recognized: ''
            })
            if (this.state.questionsCount === 1){
                this.setState({
                    buttonComment: 'Закончить'
                })
            }
            var millisecondsToWait = 500;
            setTimeout(
                this.getContent()
            , millisecondsToWait);
        }else {
            AuthenticationService.checkAndGetNext(this.state.id, this.state.recognized, this.state.questionId)
                .then(value => {
                    console.log(value);
                })
                .catch(reason => console.log(reason))
                .finally(()=> {
                    window.location.href="/evaluations/" + this.state.id
                })
        }

    }

    toRecord(){
        if (!this.state.record){
            this.setState({
                imgSrc: '../img/microrec.png'
            })
            this.startRecording();
        }else {
            this.setState({
                imgSrc: '../img/micro.png'
            })
            this.stopRecording();
        }
    }


    render() {
        return (
            <div>
                <Header/>
                <Navbar/>
                <div className={'container'}>

                    <div className="section">
                        <div className="container">

                            <div id="store" className="col-md-5">
                                <img src={"../img/demand-g.png"} width={"200px"}/>
                                <div>{this.state.questionText}</div>

                                <ReactAudioPlayer
                                    src={"http://localhost:8080/api/audio/" + this.state.audioUrl}
                                    autoPlay={true}
                                    controls
                                />

                            </div>
                            <div id="store" className="col-md-7">

                                <div>
                                    <div className="col-md-4">
                                        <ReactMic
                                            record={this.state.record}
                                            className="sound-wave"
                                            onStop={this.onStop}
                                            onData={this.onData}
                                            strokeColor="#0000f0"
                                            backgroundColor="#FFFFFF"
                                        />
                                    </div>
                                    <div className="col-md-3">
                                        {/* <button onClick={this.startRecording} type="button">Start</button>
                                        <button onClick={this.stopRecording} type="button">Stop</button>*/}
                                        <img src={this.state.imgSrc} onClick={this.toRecord} type="button" width={"200px"}/>

                                    </div>

                                </div>


                            </div>
                            <div id="store" className="col-md-7">
                                <label>
                                    {this.state.recognitionStatus}
                                    <br/>
                                    <textarea value={this.state.recognized} onChange={this.handleChange} style={{width: "560px", height: "200px"}}/>
                                    <br/>
                                    <button onClick={this.checkAndGetNext}>{this.state.buttonComment}</button>
                                </label>
                                <div>Количество вопросов, на которые предостоит ответить: {this.state.questionsCount}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Interview
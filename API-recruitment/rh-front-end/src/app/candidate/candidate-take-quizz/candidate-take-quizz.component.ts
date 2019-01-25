import { Component, OnInit } from '@angular/core';
import 'notyf/dist/notyf.min.css';
import { Router, ActivatedRoute } from '@angular/router';
import { CandidateService } from '../candidate.service';
declare var require: any;

@Component({
  selector: 'app-candidate-take-quizz',
  templateUrl: './candidate-take-quizz.component.html',
  styleUrls: ['./candidate-take-quizz.component.css']
})
export class CandidateTakeQuizzComponent implements OnInit {

  jwtToken : string;
  expToken : string;
  quizzId : any;
  questions : any;
  currentQuestion : any;
  currentAnswer : any;
  timer : any;
  questionProgress : number;
  seconds : number = 0;
  started : boolean = false;
  finished : boolean = false;
  next : number = 0;
  results = [];
  candidate : any;
  quizz ; any;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : CandidateService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.loadToken();
    if(!this.jwtToken) {
      this.notyf2.alert("user not connected !");
      this.router.navigate(['login']);
    }
    this.getFullQuizz();
    this.getConnectedCandidate();
  }

  loadToken(){
    this.jwtToken = localStorage.getItem('token');
  }

  getConnectedCandidate(){
    this.service.getConnectedCandidate().subscribe(resp => {
      this.candidate = resp.body;
    }, err => {
      this.notyf2.alert("Error geting connected user !");
      this.router.navigate(['login']);
    });
  }

  getFullQuizz(){
    this.route.params.subscribe(params => {
      this.quizzId = params['id'];
      this.expToken = params['token'];
    });

    this.service.getFullQuizz(this.quizzId, this.expToken).subscribe(resp => {
      this.questions = resp.body;
      console.log(this.questions);
    }, err => {
      this.notyf2.alert("Get Quizz failed with status "+err.status);
      this.router.navigate(['candidate/profil']);
    })
  }

  startQuizz(){
    this.started = true;
    this.currentQuestion = this.questions[this.next];
    this.timer=setInterval(()=>{
      if(this.seconds<60){
        this.seconds = this.seconds + 1;
        this.questionProgress=100*(this.seconds/60)
      }else {
        this.nextQuestion();
        return;
      }
    },1000);
  }

  displayTimeElapsed(){
    var a = Math.floor(this.seconds / 3600);
    var b =Math.floor(this.seconds/60);
     var c =Math.floor(this.seconds%60);
     return ''+a+' : '+b+' : '+c;
  }
  
  nextQuestion(){
    if(this.currentAnswer === true || this.currentAnswer === false){
      this.addAnswer();
      this.next = this.next + 1;
      if(this.next < this.questions.length){
        this.currentQuestion = this.questions[this.next];
        this.currentAnswer = "";
        clearInterval(this.timer);
        this.seconds = 0;
        this.timer=setInterval(()=>{
          if(this.seconds<60){
            this.seconds = this.seconds + 1;
            this.questionProgress=100*(this.seconds/60)
          }else {
            this.nextQuestion();
            return;
          }
        },1000);
      }else{
        // quizz ends here
        clearInterval(this.timer);
        this.finished = true;
        this.started = false;
        console.log("Quizz finished");
        this.sendResults();
      }
    }else this.notyf2.alert("Answer the question please !");
  }

  addAnswer(){
    var result = {
      "question" : this.currentQuestion,
      "candidate" : this.candidate,
      "result" : this.currentAnswer == this.currentQuestion.answer ? 1 : 0
    };
    this.results.push(result);
    console.log(result);
  }

  skipAnswer(){
    var result = {
      "question" : this.currentQuestion,
      "candidate" : this.candidate,
      "result" : 0
    };
    this.results.push(result);
    console.log(result);
  }

  sendResults(){
    this.service.sendResults(this.results).subscribe(resp => {
      this.notyf2.confirm("result sent successfuly");
    }, err => {
      this.notyf2.alert("Error sending result with status "+err.status);
    })
  } 

  skipQuestion(){
    this.skipAnswer();
    this.next = this.next + 1;
    if(this.next < this.questions.length){
      this.currentQuestion = this.questions[this.next];
      this.currentAnswer = "";
      clearInterval(this.timer);
      this.seconds = 0;
      this.timer=setInterval(()=>{
        if(this.seconds<60){
          this.seconds = this.seconds + 1;
          this.questionProgress=100*(this.seconds/60)
        }else {
          this.nextQuestion();
          return;
        }
      },1000);
    }else{
      // quizz ends here
      clearInterval(this.timer);
      this.finished = true;
      this.started = false;
      console.log("Quizz finished");
      this.sendResults();
    }
  }

}

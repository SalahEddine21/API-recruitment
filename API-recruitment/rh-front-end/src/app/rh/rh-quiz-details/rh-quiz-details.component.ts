import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RhService } from '../rh.service';
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-rh-quiz-details',
  templateUrl: './rh-quiz-details.component.html',
  styleUrls: ['./rh-quiz-details.component.css']
})
export class RhQuizDetailsComponent implements OnInit {

  quizzId : any;
  questions : any;
  partNumber : number;
  questionNumber : number;
  currentQuestion : any;
  nextDisabled : boolean;
  preDisabled : boolean = true;
  parts : any[] = new Array;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : RhService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.quizzId = params['id'];
      if(!this.quizzId) this.router.navigate(['rh/quizz/list']);
      else this.loadFullQuizz();
      this.partNumber = 0;
      this.questionNumber = 0;
    });

  }

  loadFullQuizz(){
    this.service.loadQuizz(this.quizzId).subscribe(resp => {
      this.questions = resp.body;
      this.currentQuestion = this.questions[this.questionNumber];
      if(this.questions.length == 1) this.nextDisabled = true;
      else this.nextDisabled = false;
      console.log(this.questions);
      this.getParts();
    }, err => {
      this.notyf2.alert("Error load Quizz with status "+ err.status);
    })
  }

  getParts(){
    this.parts[0] = this.questions[0].part;
    var i=0;
    this.questions.forEach(question => {
      if(this.parts[i].title != question.part.title){
        i = i + 1;
        this.parts[i] = question.part;
      }
    });
    console.log(this.parts);
  }

  nextQuestion(){
    this.questionNumber = this.questionNumber + 1;
    if(this.questionNumber < this.questions.length){
      if(this.currentQuestion.part.title != this.questions[this.questionNumber].part.title) this.partNumber = this.partNumber + 1;
      this.currentQuestion = this.questions[this.questionNumber];
      if((this.questionNumber + 1) == this.questions.length) this.nextDisabled = true;
    }
    this.preDisabled = false;
  }

  preQuestion(){
    if(this.questionNumber > 0){
      this.questionNumber = this.questionNumber - 1;
      if(this.currentQuestion.part.title != this.questions[this.questionNumber].part.title) this.partNumber = this.partNumber - 1;
      this.currentQuestion = this.questions[this.questionNumber];
      if(this.questionNumber == 0) this.preDisabled = true;
    }
    this.nextDisabled = false;
  }

}

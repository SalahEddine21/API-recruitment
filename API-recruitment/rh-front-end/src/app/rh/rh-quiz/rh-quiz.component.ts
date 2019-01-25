import { Component, OnInit } from '@angular/core';
import { Question } from '../Question';
import { Quiz } from '../Quiz';
import { Part } from '../Part';
import { Router } from '@angular/router';
import { RhService } from '../rh.service';
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-rh-quiz',
  templateUrl: './rh-quiz.component.html',
  styleUrls: ['./rh-quiz.component.css']
})
export class RhQuizComponent implements OnInit {

  quiz : Quiz = new Quiz();
  part : Part;
  partNumber : number;
  questionNumber : number;
  question : Question;
  continue = false;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : RhService, private router: Router) { }

  ngOnInit() {
    this.partNumber = 0;
    this.questionNumber = 0;
  } 

  createQuiz(){
    this.continue = true;
    this.part = new Part();
    this.question = new Question();
    this.partNumber = this.partNumber + 1;
    this.questionNumber = this.questionNumber + 1;
  }

  addQuestion(){
    if(this.question.content && (this.question.answer==false || this.question.answer==true) ){
      this.part.questions.push(this.question);
      this.question = new Question();
      this.questionNumber = this.questionNumber + 1;
    }else this.notyf2.alert("question is required !");
  }

  addPart(){
    if(this.part.questions.length > 0){
      this.quiz.parts.push(this.part);
      this.part = new Part();
      this.question = new Question();
      this.partNumber = this.partNumber + 1;
      this.questionNumber = 1;
      console.log(this.quiz);
      this.notyf2.confirm("Part added");
    }else this.notyf2.alert("a part must have at less one question !");
  }

  validateQuiz(){
    if(this.quiz.parts.length > 0){
      console.log(this.quiz);
      this.service.sendQuizz(this.quiz).subscribe(resp => {
        this.notyf2.confirm("Quizz created");
        // move to list of quizzs
      }, err => {
        this.notyf2.alert("Quizz create failed with status "+err.status);
      });
    }else this.notyf2.alert("a quizz must have at less one part !");
  }

  openQuizzList(){
    this.router.navigate(['rh/quizz/list']);
  }

}

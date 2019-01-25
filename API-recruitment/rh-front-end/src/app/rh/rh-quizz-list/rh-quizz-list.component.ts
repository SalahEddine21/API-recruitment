import { Component, OnInit } from '@angular/core';
import { RhService } from '../rh.service';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
import { Router } from '@angular/router';
import { CandidateService } from 'src/app/candidate/candidate.service';
import { Button } from 'protractor';
declare var require: any;

@Component({
  selector: 'app-rh-quizz-list',
  templateUrl: './rh-quizz-list.component.html',
  styleUrls: ['./rh-quizz-list.component.css']
})
export class RhQuizzListComponent implements OnInit {

  quizz : any;
  employee;
  closeResult : string;
  candidates : any;
  cv : any;
  currentQuizz : any;
  apiUrl = environment.apiUrl;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : RhService, private router: Router, private modalService: NgbModal, private candidateService : CandidateService) { }

  ngOnInit() {
    this.getConnectedEmployee();
  }

  getConnectedEmployee(){
    this.service.getConnectedEmployee().subscribe(resp=>{
      this.employee = resp.body;
      this.getEmployeeQuizz();
    }, err => {
      this.notyf2.alert("Error getting connected user with status "+err.status);
    });
  }

  getEmployeeQuizz(){
    this.service.getEmployeeQuizz(this.employee.id).subscribe(resp => {
      this.quizz = resp.body;
      console.log("-------------")
      console.log(this.quizz);
    }, err => {
      this.notyf2.alert("failed to get quizz with status "+err.status);
    })
  }

  detailQuiz(quiz){
    this.router.navigate(['rh/quiz/details/'+quiz.id]);
  }

  open(content, quizz) {
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   
    this.getAllCandidates();
    this.currentQuizz = quizz;
    console.log(this.currentQuizz);
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  openCandidatesByQuizz(content, quizz) {
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   
    this.currentQuizz = quizz;
    this.getCandidatesByQuizz(quizz);
  }

  getAllCandidates(){
    this.service.getAllCandidates().subscribe(resp => {
      this.candidates = resp.body;
      console.log("candidates are ");
      console.log(this.candidates);
    }, err => {
      this.notyf2.alert("Error getting candidates list with status "+err.status);
    });
  }

  getImageUrl(candidate){
    if(candidate.photo) return this.apiUrl+'candidate/photo/'+candidate.photo;
    return candidate.sexe == 'M' ? "../../../assets/images/candidateM.png" : "../../../assets/images/candidateF.png";
  }

  getCv(candidate){
    this.candidateService.getVisibleCvId(candidate.id).subscribe(resp => {
      this.cv = resp.body;
      window.open(this.apiUrl+'cv/'+this.cv.id);
    }, err => {
      this.notyf2.alert("get cv failed with status "+err.status);
    })
  }

  sendQuizz(candidate, event){
    this.service.convocateQuizz(candidate, this.currentQuizz.id).subscribe(resp => {
      event.target.setAttribute("disabled",true); 
      this.notyf2.confirm(candidate.name+' '+candidate.lastname+' was convocated successfully');
    }, err => {
      this.notyf2.alert("Error convocation with status "+err.status);
    })
  }

  getCandidatesByQuizz(quizz){
    this.service.getCandidatesByQuizz(quizz.id).subscribe(resp => {
      this.candidates = resp.body;
    }, err => {
      this.notyf2.alert("Error getting candidates with status "+err.status);
    })
  }

  seeResults(candidate){
    window.open('rh/result/'+candidate.id+'/'+this.currentQuizz.id);
  }

}

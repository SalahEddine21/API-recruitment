import { Component, OnInit } from '@angular/core';
import { CandidateService } from '../../candidate/candidate.service';
import { environment } from "../../../environments/environment";
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import 'notyf/dist/notyf.min.css';
import { Router } from '@angular/router';
import { RhService } from '../rh.service';
declare var require: any;

@Component({
  selector: 'app-rh-candidates-cvs',
  templateUrl: './rh-candidates-cvs.component.html',
  styleUrls: ['./rh-candidates-cvs.component.css']
})
export class RhCandidatesCvsComponent implements OnInit {

  candidates : any;
  cv : any;
  closeResult : any;
  currentCandidate : any;
  quizzs : any;
  body : any;
  subject : any;
  employee : any;
  private apiUrl = environment.apiUrl;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : CandidateService, private router: Router, 
    private modalService: NgbModal, private rhService : RhService) { }


  ngOnInit() {
    this.getAllCandidates();
    this.getConnectedEmployee();
  }

  getAllCandidates(){
    this.service.getAllCandidates().subscribe(resp => {
      this.candidates = resp.body;
      console.log(this.candidates);
    }, err => {
      this.notyf2.alert("get candidates failed with status "+err.status);
    })
  }

  getImageUrl(candidate){
    if(candidate.photo) return this.apiUrl+'candidate/photo/'+candidate.photo;
    return candidate.sexe == 'M' ? "../../../assets/images/candidateM.png" : "../../../assets/images/candidateF.png";
  }

  getCv(candidate){
    this.service.getVisibleCvId(candidate.id).subscribe(resp => {
      this.cv = resp.body;
      window.open(this.apiUrl+'cv/'+this.cv.id);
    }, err => {
      this.notyf2.alert("get cv failed with status "+err.status);
    })
  }

  open(content, candidate) {
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      // this.sendEmail();
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   
    this.currentCandidate = candidate;
    console.log(this.currentCandidate);
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

  getConnectedEmployee(){
    this.rhService.getConnectedEmployee().subscribe(resp=>{
      this.employee = resp.body;
    }, err => {
      this.notyf2.alert("Error geting connected employee with status "+ err.status);
    })
  }
  sendEmail(){
    var mail = {
      "from" : this.employee.email,
      "to" : this.currentCandidate.email,
      "subject" : this.subject,
      "body" : this.body
    };

    this.rhService.emailCandidate(mail).subscribe(resp => {
      this.notyf2.confirm("Mail sent");
    }, err => {
      this.notyf2.alert("sent failed with status "+err.status);
    })
  }

  getEmployeeQuizz(){
    this.rhService.getEmployeeQuizz(this.employee.id).subscribe(resp => {
      this.quizzs = resp.body;
      console.log("quizzs are: ");
      console.log(this.quizzs);
    }, err => {
      this.notyf2.alert("failed to get quizz with status "+err.status);
    })
  }

  openQuizzSender(content, candidate){
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   
    this.currentCandidate = candidate;
    this.getEmployeeQuizz();      
  }

  sendQuizz(quizz, event){
    this.rhService.convocateQuizz(this.currentCandidate, quizz.id).subscribe(resp => {
      event.target.setAttribute("disabled",true); 
      this.notyf2.confirm(this.currentCandidate.name+' '+this.currentCandidate.lastname+' was convocated successfully');
    }, err => {
      this.notyf2.alert("Error convocation with status "+err.status);
    })
  }

}

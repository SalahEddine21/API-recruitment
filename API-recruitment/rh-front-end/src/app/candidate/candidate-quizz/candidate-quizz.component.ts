import { Component, OnInit } from '@angular/core';
import { CandidateService } from '../candidate.service';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import 'notyf/dist/notyf.min.css';
import { Router } from '@angular/router';
import { RhService } from 'src/app/rh/rh.service';
declare var require: any;

@Component({
  selector: 'app-candidate-quizz',
  templateUrl: './candidate-quizz.component.html',
  styleUrls: ['./candidate-quizz.component.css']
})
export class CandidateQuizzComponent implements OnInit {

  quizz : any;
  candidate : any;
  results : any;
  currentQuizz : any;
  closeResult : string;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : CandidateService, private router: Router, private rhService : RhService, private modalService: NgbModal) { }

  ngOnInit() {
    this.getConnectedCandidate();
  }

  getConnectedCandidate(){
    this.service.getConnectedCandidate().subscribe(resp => {
      this.candidate = resp.body;
      this.getAllQuizz();
    }, err => {
      this.notyf2.alert("Error geting connected user !");
      this.router.navigate(['login']);
    });
  }

  getAllQuizz(){
    this.service.getAllQuizz(this.candidate.id).subscribe(resp => {
      this.quizz = resp.body;
      console.log(this.quizz);
    }, err => {
      this.notyf2.alert("Failed getting quizz with status "+err.status);
    })
  }

  getResults(){
    this.rhService.getCandidateResults(this.candidate.id, this.currentQuizz.id).subscribe(resp => {
      this.results = resp.body;
      console.log(this.results);
    }, err => {
      this.notyf2.alert("Error getting results with status "+err.status);
    })
  }

  open(content, quizz) {
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   
    this.currentQuizz = quizz;
    this.getResults();
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

}

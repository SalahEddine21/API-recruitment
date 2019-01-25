import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RhService } from '../rh.service';
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-rh-quizz-results',
  templateUrl: './rh-quizz-results.component.html',
  styleUrls: ['./rh-quizz-results.component.css']
})
export class RhQuizzResultsComponent implements OnInit {

  quizzId : any;
  candidateId : any;
  results : any;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : RhService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.quizzId = params['quizzId'];
      this.candidateId = params['candidateId'];
    });
    console.log(this.quizzId);
    console.log(this.candidateId);
    this.getResults();
  }

  getResults(){
    this.service.getCandidateResults(this.candidateId, this.quizzId).subscribe(resp => {
      this.results = resp.body;
      console.log(this.results);
    }, err => {
      this.notyf2.alert("Error getting results with status "+err.status);
    })
  }

}

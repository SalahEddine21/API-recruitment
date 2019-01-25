import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CandidateService } from '../candidate.service';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-candidate-applications',
  templateUrl: './candidate-applications.component.html',
  styleUrls: ['./candidate-applications.component.css']
})
export class CandidateApplicationsComponent implements OnInit {

  candidate : any;
  candidatures : any;
  apiUrl = environment.apiUrl;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : CandidateService, private router: Router) { }

  ngOnInit() {
    this.getConnectedCandidate();
  }

  getConnectedCandidate(){
    this.service.getConnectedCandidate().subscribe(resp => {
      this.candidate = resp.body;
      console.log(this.candidate);
      this.getCandidatures(this.candidate.id);
    }, err => {
      this.notyf2.alert("Error geting connected user !");
      this.router.navigate(['login']);
    });
  }

  getCandidatures(candidateId){
    this.service.getCandidatures(candidateId).subscribe(resp => {
      this.candidatures = resp.body;
      console.log(this.candidatures);
    }, err => {
      this.notyf2.alert("Get candidatures failed !");
    });
  }

  cancel(candidature){
    this.service.cancelCandidature(candidature.id).subscribe(resp => {
      const index: number = this.candidatures.indexOf(candidature);
      if (index !== -1) {
          this.candidatures.splice(index, 1);
      }
      this.notyf2.confirm("Application canceled");
    }, err => {
      this.notyf2.alert("Cancel failed !");
    })
  }

  openCv(cv){
    window.open(this.apiUrl+'cv/'+cv.id);
  }
}

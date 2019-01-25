import { Component, OnInit } from '@angular/core';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
import { Router } from '@angular/router';
import { CandidateService } from '../candidate.service';
declare var require: any;

@Component({
  selector: 'app-candidate-navbar',
  templateUrl: './candidate-navbar.component.html',
  styleUrls: ['./candidate-navbar.component.css']
})
export class CandidateNavbarComponent implements OnInit {

  candidate : any;
  imageUrl : string;
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
      if(this.candidate.photo) this.imageUrl = this.apiUrl+'candidate/photo/'+this.candidate.photo
      else  this.imageUrl = "../../../assets/images/candidate.png";
      // console.log(this.candidate);
    }, err => {
      this.notyf2.alert("Error geting connected user !");
      this.router.navigate(['login']);
    });
  }

  disconnect(){
    localStorage.removeItem('token');
    this.router.navigate(['']);
  }

}

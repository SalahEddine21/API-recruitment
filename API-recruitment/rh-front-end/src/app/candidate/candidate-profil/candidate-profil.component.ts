import { Component, OnInit } from '@angular/core';
import { CandidateService } from '../candidate.service';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
import { Router } from '@angular/router';
declare var require: any;

@Component({
  selector: 'app-candidate-profil',
  templateUrl: './candidate-profil.component.html',
  styleUrls: ['./candidate-profil.component.css']
})
export class CandidateProfilComponent implements OnInit {

  candidate : any;
  cvs : any;
  imageUrl : string;
  cv : any;
  apiUrl = environment.apiUrl;
  currentFileUpload: File;
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
      this.getCvs(this.candidate.id);
      console.log(this.candidate);
    }, err => {
      this.notyf2.alert("Error geting connected user !");
      this.router.navigate(['login']);
    });
  }

  getCvs(candidateId){
    this.service.getCvs(candidateId).subscribe(resp => {
      this.cvs = resp.body;
      console.log(this.cvs);
    }, err => {
      this.notyf2.alert("Error geting cvs !");
    })
  }

  openCv(cv){
    window.open(this.apiUrl+'cv/'+cv.id);
  }

  deleteCv(cv){
    this.service.deleteCv(cv.id).subscribe(resp => {
      const index: number = this.cvs.indexOf(cv);
      if (index !== -1) {
          this.cvs.splice(index, 1);
      }
    }, err => {
      this.notyf2.alert("Delete failed !");
    })
  }

  selectFile(event){
    this.currentFileUpload = event.target.files.item(0);
  }

  uploadCv(){
    this.service.uploadCv(this.candidate.id, this.currentFileUpload).subscribe(resp => {
      this.cv = resp.body;
      this.cvs.push(this.cv);
      this.notyf2.confirm("Upload success");
    }, err => {
      this.notyf2.alert("Upload Failed !");
    })
  }

}

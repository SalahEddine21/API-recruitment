import { Component, OnInit } from '@angular/core';
import { RhService } from '../rh.service';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from "../../../environments/environment";
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import 'notyf/dist/notyf.min.css';
declare var require: any;


@Component({
  selector: 'app-rh-offre-candidatures',
  templateUrl: './rh-offre-candidatures.component.html',
  styleUrls: ['./rh-offre-candidatures.component.css']
})
export class RhOffreCandidaturesComponent implements OnInit {

  candidatures : any;
  offreID;
  body : string;
  currentCandidature : any;
  private apiUrl = environment.apiUrl;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });
  private closeResult: string;



  constructor(private rhService: RhService, private router : Router, private route: ActivatedRoute, private modalService: NgbModal) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.offreID = params['id'];
      if(this.offreID) this.getCandidatures();
      else console.log("ID not setted !");
    });
  }

  getCandidatures(){
    this.rhService.getCandidatues(this.offreID).subscribe(resp => {
      this.candidatures = resp.body;
      console.log(this.candidatures);
    }, err => {
      this.notyf2.alert("error geting candidatures ! ");
    })
  }

  getImageUrl(photoname){
    var url = this.apiUrl+'candidate/photo/'+photoname;
    console.log(url)
    return url ;
  }

  getCV(cv){
    window.open(this.apiUrl+'cv/'+cv.id);
  }

  open(content, candidature) {
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      this.convocateCandidate(this.currentCandidature.offre.title, this.currentCandidature.candidate.email, this.body);
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   
    this.currentCandidature = candidature;
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

  convocateCandidate(subject, to, body){
    var email = {
      "subject" : subject,
      "to" : to,
      "body" : body
    };

    console.log(email);

    this.rhService.convocateCandidate(email).subscribe(resp => {
      this.notyf2.confirm("Mail sent success");
    }, err => {
      this.notyf2.alert("Mail sent failed !");
    });

  }
}

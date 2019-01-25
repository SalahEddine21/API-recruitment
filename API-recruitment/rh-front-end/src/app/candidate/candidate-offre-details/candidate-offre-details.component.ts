import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CandidateService } from '../candidate.service';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-candidate-offre-details',
  templateUrl: './candidate-offre-details.component.html',
  styleUrls: ['./candidate-offre-details.component.css']
})
export class CandidateOffreDetailsComponent implements OnInit {

  offreID : any;
  offre : any;
  candidate : any;
  body : string;
  cvs : any;
  cvId : any;
  private apiUrl = environment.apiUrl;
  private closeResult: string;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });
  
  constructor(private service : CandidateService, private router: Router, private route: ActivatedRoute, private modalService: NgbModal) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.offreID = params['id'];
      console.log(this.offreID);
      if(this.offreID) this.getOffre(this.offreID);
    });

    this.getConnectedCandidate();
  }

  getConnectedCandidate(){
    this.service.getConnectedCandidate().subscribe(resp => {
      this.candidate = resp.body;
      this.getCvs(this.candidate.id);
      console.log(this.candidate);
    }, err => {
      this.notyf2.alert("Error geting connected user !");
      this.router.navigate(['login']);
    });
  }

  getOffre(offreID){
    this.service.getOffre(offreID).subscribe(resp => {
      this.offre = resp.body;
      this.offre.id = offreID;
      console.log(this.offre);
    }, err => {
      this.notyf2.alert("Error getting offre details !");
    })
  }

  open(content, candidature) {
    this.modalService.open(content, { size: 'lg' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      this.apply();
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });   

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

  getCvs(candidateId){
    this.service.getCvs(candidateId).subscribe(resp => {
      this.cvs = resp.body;
      if(this.cvs.length == 1) this.cvId = this.cvs[0].id;
      console.log(this.cvs);
    }, err => {
      this.notyf2.alert("Error geting cvs !");
    })
  }

  openCv(cv){
    window.open(this.apiUrl+'cv/'+cv.id);
  }

  apply(){
    var mail = {
      "subject" : this.offre.title,
      "to" : this.offre.employee.email,
      "from" : this.candidate.email,
      "body" : this.body,
      "cvid" : this.cvId
    };

    this.service.applyJob(mail).subscribe(resp => {
      this.notyf2.confirm("mail sent")
    }, err => {
      this.notyf2.alert("sent failed!");
    });

    console.log(mail);
  }
}

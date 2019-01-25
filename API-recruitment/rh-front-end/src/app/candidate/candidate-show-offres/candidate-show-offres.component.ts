import { Component, OnInit } from '@angular/core';
import { CandidateService } from '../candidate.service';
import { FormGroup, FormControl } from '@angular/forms';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
import { Router } from '@angular/router';
declare var require: any;

@Component({
  selector: 'app-candidate-show-offres',
  templateUrl: './candidate-show-offres.component.html',
  styleUrls: ['./candidate-show-offres.component.css']
})
export class CandidateShowOffresComponent implements OnInit {

  offres : any;
  types : any;
  domaines : any;
  type : any;
  domaine : any;
  showloader : boolean = false;
  cities = ["Casablanca", "Rabat", "Marrakech", "Eljadida"];
  city :any;
  apiUrl = environment.apiUrl;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : CandidateService, private router: Router) { }

  ngOnInit() {
    this.getallOffres();
    this.getTypes();
    this.getDomaines();
    console.log(this.cities);
  }

  getallOffres(){
    this.service.getallOffres().subscribe(resp => {
      this.offres = resp.body;
      console.log(this.offres);
    }, err => {
      this.notyf2.alert("get Offres failed !");
    })
  }

  getTypes(){
    this.service.getTypes().subscribe(resp => {
      this.types = resp.body;
      console.log(this.types);
    }, err => {
      console.log("error getting types from server !");
    });
  }

  getDomaines(){
    this.service.getDomaines().subscribe(resp => {
      this.domaines = resp.body;
      console.log(this.domaines);
    }, err => {
      console.log("error getting domaines from server !");
    })
  }

  getimageUrl(photo){
    if(photo) return this.apiUrl+'offre/image/'+photo;
    return "../../../assets/images/offre.png";
  }

  updateOffres(){
    if(this.city) console.log(this.city);
    if(this.type) console.log(this.type);
    if(this.domaine) console.log(this.domaine);

    var data = {
      "city" : this.city,
      "type" : this.type,
      "domaine" : this.domaine
    }
    this.showloader = true;
    this.service.searchOffres(data).subscribe(resp => {
      this.offres = resp.body;
      this.showloader = false;
      console.log(this.offres);
    }, err => {
      this.showloader = false;
      this.notyf2.alert("search failed !");
    })
  }
}

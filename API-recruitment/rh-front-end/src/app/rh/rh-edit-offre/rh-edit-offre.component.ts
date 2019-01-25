import { Component, OnInit } from '@angular/core';
import { RhService } from '../rh.service';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from "../../../environments/environment";
import 'notyf/dist/notyf.min.css';
import { Offre } from '../offre';
declare var require: any;


@Component({
  selector: 'app-rh-edit-offre',
  templateUrl: './rh-edit-offre.component.html',
  styleUrls: ['./rh-edit-offre.component.css']
})
export class RhEditOffreComponent implements OnInit {

  offreID;
  offre : any;
  imageUrl : string;
  types;
  domaines;
  apiUrl = environment.apiUrl;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private rhService: RhService, private router : Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.offreID = params['id'];
      console.log(this.offreID);
      if(this.offreID) {
        this.getOffre(this.offreID);
        this.getTypes();
        this.getDomaines();
      }
    });
  }

  getOffre(offreID){
    this.rhService.getOffre(offreID).subscribe(resp => {
      this.offre = resp.body;
      this.offre.id = offreID;
      if(this.offre.photo) this.imageUrl = this.apiUrl + 'offre/image/'+this.offre.photo;
      console.log(this.offre);
    }, err => {
      this.notyf2.alert("Error getting offre details !");
    })
  }

  getTypes(){
    this.rhService.getTypes().subscribe(resp => {
      this.types = resp.body;
      console.log(this.types);
    }, err => {
      console.log("error getting types from server !");
    });
  }

  getDomaines(){
    this.rhService.getDomaines().subscribe(resp => {
      this.domaines = resp.body;
      console.log(this.domaines);
    }, err => {
      console.log("error getting domaines from server !");
    })
  }

  editOffre(){
    console.log(this.offre);
    this.rhService.editOffre(this.offre).subscribe(resp => {
      this.notyf2.confirm("Edited");
    }, err => {
      this.notyf2.alert("Edit failed !");
    })
  }

}

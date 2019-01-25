import { Component, OnInit } from '@angular/core';
import { Offre } from "../offre";
import { FormGroup, FormControl } from '@angular/forms';
import { RhService } from '../rh.service';
@Component({
  selector: 'app-rh-create-offre',
  templateUrl: './rh-create-offre.component.html',
  styleUrls: ['./rh-create-offre.component.css']
})
export class RhCreateOffreComponent implements OnInit {

  offre : Offre = new Offre();
  types;
  domaines;
  currentFileUpload: File;
  
  constructor(private rhService : RhService) { }

  ngOnInit() {
    this.getTypes();
    this.getDomaines();
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

  selectFile(event){
    this.currentFileUpload = event.target.files.item(0);
  }

  sendData(){
    console.log(this.offre);
    console.log(this.currentFileUpload);
    this.rhService.saveOffre(this.currentFileUpload, this.offre).subscribe(resp => {
      console.log("save success");
    }, err =>{
      console.log("save error !");
    });
  }

}

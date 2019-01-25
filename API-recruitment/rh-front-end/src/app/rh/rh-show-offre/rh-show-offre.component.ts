import { Component, OnInit } from '@angular/core';
import { RhService } from '../rh.service';
import { environment } from "../../../environments/environment";
import { Router } from '@angular/router';
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-rh-show-offre',
  templateUrl: './rh-show-offre.component.html',
  styleUrls: ['./rh-show-offre.component.css']
})
export class RhShowOffreComponent implements OnInit {

  private apiUrl = environment.apiUrl;
  currentImageUrl : string;
  employee;
  offres;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private rhService : RhService, private router: Router) { }

  ngOnInit() {
    this.getConnectedEmployee();
  }

  getOffres(){
    this.rhService.getOffresByEmployee(this.employee.id).subscribe(resp => {
      this.offres = resp.body;
      console.log(this.offres);
    }, err => {
      console.log("offres error!");
    })
  }

  getConnectedEmployee(){
    this.rhService.getConnectedEmployee().subscribe(resp=>{
      this.employee = resp.body;
      this.getOffres();
    });
  }

  getImage(name){
    this.currentImageUrl = this.apiUrl+'offre/image/'+name;
    return this.currentImageUrl;
  }

  getCandidatues(offre){
    this.router.navigate(['rh/offre/'+offre.id+'/candidatures']);
  }

  seeDetails(offre){
    this.router.navigate(['rh/offre/'+offre.id]);
  }

  deleteOffre(offre){
    this.rhService.deleteOffre(offre.id).subscribe(resp => {
      const index: number = this.offres.indexOf(offre);
      if (index !== -1) {
          this.offres.splice(index, 1);
      }
      this.notyf2.confirm("Offre deleted");
    }, err => {
      this.notyf2.alert("Delete failed !");
    })
  }
}

import { Component, OnInit } from '@angular/core';
import { RhService } from '../rh.service';
import { environment } from "../../../environments/environment";
import { Router } from '@angular/router';

@Component({
  selector: 'app-rh-navbar',
  templateUrl: './rh-navbar.component.html',
  styleUrls: ['./rh-navbar.component.css']
})
export class RhNavbarComponent implements OnInit {

  employee : any;
  imageUrl : string;
  apiUrl = environment.apiUrl;

  constructor(private rhService : RhService, private router: Router) { }

  ngOnInit() {
    console.log("inside rh navbar component");
    this.getConnectedEmployee();
  }

  getConnectedEmployee(){
    this.rhService.getConnectedEmployee().subscribe(resp=>{
      this.employee = resp.body;
      if(!this.employee.photo) this.imageUrl = "../../../assets/images/candidate.png";
      else this.imageUrl = this.apiUrl + "rh/photo/"+this.employee.photo;
      
      console.log(this.imageUrl);
      console.log(this.employee);
    });
  }

  createOffre(){
    this.router.navigate(['rh/offre/create']);
  }
  showOffres(){
    this.router.navigate(['rh/offres']);
  }

  disconnect(){
    localStorage.removeItem('token');
    this.router.navigate(['']);
   }
  
}

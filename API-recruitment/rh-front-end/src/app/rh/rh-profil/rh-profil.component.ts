import { Component, OnInit } from '@angular/core';
import { RhService } from '../rh.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-rh-profil',
  templateUrl: './rh-profil.component.html',
  styleUrls: ['./rh-profil.component.css']
})
export class RhProfilComponent implements OnInit {

  employee : any;
  imageUrl : string;
  logoUrl : string;
  apiUrl = environment.apiUrl;

  constructor(private rhService : RhService) { }

  ngOnInit() {
    console.log("inside profil component!");
    this.getConnectedEmployee();
  }

  getConnectedEmployee(){
    this.rhService.getConnectedEmployee().subscribe(resp=>{
      this.employee = resp.body;
      if(!this.employee.photo) this.imageUrl = "../../../assets/images/candidate.png";
      else this.imageUrl = this.apiUrl + "rh/photo/" +this.employee.photo;

      this.logoUrl = this.apiUrl + "entreprise/logo/"+this.employee.entreprise.photo;
      console.log(this.employee);
    })
  }

}

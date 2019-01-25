import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { Router } from '@angular/router';
import 'notyf/dist/notyf.min.css';
declare var require: any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username : string;
  password : string;
  user : string;
  jwtToken : string;
  connected : any;
  private Notyf = require('notyf');
  private notyf2 = new this.Notyf({
      delay:3000,
      alertIcon: 'fa fa-exclamation-circle',
      confirmIcon: 'fa fa-check-circle'  
  });

  constructor(private service : AppService, private router: Router) { }

  ngOnInit() {
    this.loadToken();
    if(this.jwtToken){
      this.currentUser();
      if(this.user === 'rh') this.router.navigate(['rh/profil']);
      else this.router.navigate(['candidate/profil']);
    }
  }

  loadToken(){
    this.jwtToken = localStorage.getItem('token');
  }

  currentUser(){
    this.user = localStorage.getItem('user');
  }

  Submit(){
    this.service.login(this.username, this.password).subscribe(resp=>{
      let jwt=resp.headers.get('authorization');
      this.connected = resp.body;
      console.log(this.connected.user);
      localStorage.setItem('user', this.connected.user);
      this.service.saveToken(resp.headers.get('authorization'));

      if(this.connected.user=='rh') this.router.navigate(['rh/profil']);
      else this.router.navigate(['candidate/profil']);
    }, err=>{
      console.log("an error occured!");
    });
  }


}

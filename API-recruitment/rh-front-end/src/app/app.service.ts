import { Injectable } from '@angular/core';
import { environment } from "../environments/environment";
import { Http } from "@angular/http";
import {HttpClient, HttpRequest, HttpParams, HttpResponse, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private apiUrl = environment.apiUrl;
  private jwtToken=null;

  constructor(private http : Http, private httpclient : HttpClient) { }

  login(username, password){
    return this.httpclient.post(this.apiUrl+'login',{username: username, password: password},{observe :'response'})
  }

  saveToken(jwt: string){
    localStorage.setItem('token', jwt);
  }
}

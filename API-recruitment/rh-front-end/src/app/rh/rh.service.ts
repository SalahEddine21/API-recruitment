import { Injectable } from '@angular/core';
import { Http } from "@angular/http";
import {HttpClient, HttpRequest, HttpParams, HttpResponse, HttpHeaders} from '@angular/common/http';
import { environment } from "../../environments/environment";
import { Offre } from './offre';
import { Quiz } from './Quiz';
@Injectable({
  providedIn: 'root'
})
export class RhService {

  private apiUrl = environment.apiUrl;
  private jwtToken : string = null;

  constructor(private httpclient: HttpClient, private http : Http) { }

  loadToken(){
    this.jwtToken = localStorage.getItem('token');
  }

  getConnectedEmployee(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'rh/connectedUser', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getTypes(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/types', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getDomaines(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/domaines', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  saveOffre(file : File, offre : Offre){
    this.loadToken();
    let formdata: FormData = new FormData();
    formdata.append('file', file);

    const blobOffre = new Blob([JSON.stringify(offre)], { type: "application/json"});
    formdata.append('offre', blobOffre);
    
    const req = new HttpRequest('POST', this.apiUrl+'offre/save', formdata , {
      reportProgress: true,
      responseType: 'text',
      headers:new HttpHeaders({'authorization':this.jwtToken} )
    });
  
    return this.httpclient.request(req);
    // formdata.append('file', file);
    // return this.httpclient.post(this.apiUrl+'offre/save', {offre, formdata}, {observe :'response'});
  }

  getOffresByEmployee(id){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/employee/all', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getOffreImage(name){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/image/'+name, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getCandidatues(offreId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'candidature/offre/'+offreId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  convocateCandidate(email){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'candidate/convocate', email, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getOffre(offreId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/'+offreId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  editOffre(offre){
    this.loadToken();
    return this.httpclient.put(this.apiUrl+'offre/edit', offre, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  deleteOffre(offreId){
    this.loadToken();
    return this.httpclient.put(this.apiUrl+'offre/delete/'+offreId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  emailCandidate(mail){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'candidate/mailing', mail, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  sendQuizz(quizz : Quiz){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'quizz/add', quizz, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getEmployeeQuizz(employeeId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'quizz/employee/'+employeeId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getAllCandidates(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'candidate/all', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  convocateQuizz(candidate, quizzId){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'candidate/convocate/quizz/'+quizzId, candidate, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getCandidatesByQuizz(quizzId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'result/candidates/'+quizzId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getCandidateResults(candidateId, quizzId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'result/candidate/'+candidateId+'/quizz/'+quizzId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  loadQuizz(quizzId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'quizz/details/'+quizzId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }
}

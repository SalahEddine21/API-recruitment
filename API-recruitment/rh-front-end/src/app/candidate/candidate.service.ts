import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  private jwtToken : string;
  private apiUrl = environment.apiUrl;

  constructor(private http : Http, private httpclient : HttpClient) { }

  loadToken(){
    this.jwtToken = localStorage.getItem('token');
  }

  getConnectedCandidate(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'candidate/connectedUser', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getCvs(candidateId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'cv/candidate/'+candidateId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  deleteCv(cvId){
    this.loadToken();
    return this.httpclient.delete(this.apiUrl+'cv/'+cvId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  uploadCv(candidateId : any, cv : File){
    this.loadToken();
    let formdata: FormData = new FormData();
    formdata.append('file', cv);
    return this.httpclient.post(this.apiUrl+'cv/upload/'+candidateId, formdata, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getallOffres(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/all', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getTypes(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/types', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getDomaines(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/domaines', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  searchOffres(data){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'offre/search', data, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getOffre(offreId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'offre/'+offreId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  applyJob(email){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'offre/apply', email, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getCandidatures(candidateId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'candidature/candidate/'+candidateId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  cancelCandidature(candidatureId){
    this.loadToken();
    return this.httpclient.delete(this.apiUrl+'candidatures/cancel/'+candidatureId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getAllCandidates(){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'candidate/all', {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'})
  }

  getVisibleCvId(candidateId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'cv/visible/'+candidateId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'}) 
  }

  getFullQuizz(quizzId, expToken : string){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'quizz/full/'+quizzId, expToken, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'}) 
  }

  getQuizz(quizzId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'quizz/get/'+quizzId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'}) 
  }

  sendResults(results){
    this.loadToken();
    return this.httpclient.post(this.apiUrl+'result/save', results, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'}) 
  }

  getAllQuizz(candidateId){
    this.loadToken();
    return this.httpclient.get(this.apiUrl+'quizz/candidate/'+candidateId, {headers:new HttpHeaders({'authorization':this.jwtToken}),observe:'response'}) 
  }
}

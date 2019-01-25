import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { RhModule } from "./rh/rh.module";
import { CandidateModule } from "./candidate/candidate.module";
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from "./app-routing.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import { LoginComponent } from './login/login.component';
import { AppService } from './app.service';
import { HttpModule } from '@angular/http';
import { HttpClientModule, HttpClient} from '@angular/common/http';
 
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    RhModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    HttpModule,
    HttpClientModule,
    CandidateModule
  ],
  providers: [AppService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }

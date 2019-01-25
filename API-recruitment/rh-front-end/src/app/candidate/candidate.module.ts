import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CandidateRoutingModule } from './candidate-routing.module';
import { CandidateProfilComponent } from './candidate-profil/candidate-profil.component';
import { CandidateNavbarComponent } from './candidate-navbar/candidate-navbar.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatMenuModule, MatButtonModule, MatIconModule} from '@angular/material';
import {MatDividerModule} from '@angular/material/divider';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import {MatRadioModule} from '@angular/material/radio';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { CandidateShowOffresComponent } from './candidate-show-offres/candidate-show-offres.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { CandidateOffreDetailsComponent } from './candidate-offre-details/candidate-offre-details.component';
import { CandidateApplicationsComponent } from './candidate-applications/candidate-applications.component';
import { CandidateTakeQuizzComponent } from './candidate-take-quizz/candidate-take-quizz.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { CandidateQuizzComponent } from './candidate-quizz/candidate-quizz.component';

@NgModule({
  imports: [
    CommonModule,
    CandidateRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatTabsModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatInputModule,
    MatListModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTableModule,
    NgbModule,
    MatRadioModule,
    MatCheckboxModule,
    MatProgressBarModule
  ],
  declarations: [CandidateProfilComponent, CandidateNavbarComponent, CandidateShowOffresComponent, CandidateOffreDetailsComponent, CandidateApplicationsComponent, CandidateTakeQuizzComponent, CandidateQuizzComponent]
})
export class CandidateModule { }

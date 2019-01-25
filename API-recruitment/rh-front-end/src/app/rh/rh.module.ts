import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RhRoutingModule } from './rh-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import {MatMenuModule, MatButtonModule, MatIconModule} from '@angular/material';
import {MatDividerModule} from '@angular/material/divider';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import { RhNavbarComponent } from './rh-navbar/rh-navbar.component';
import { RhProfilComponent } from './rh-profil/rh-profil.component';
import { RhCreateOffreComponent } from './rh-create-offre/rh-create-offre.component';
import { RhShowOffreComponent } from './rh-show-offre/rh-show-offre.component';
import { RhOffreCandidaturesComponent } from './rh-offre-candidatures/rh-offre-candidatures.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { RhEditOffreComponent } from './rh-edit-offre/rh-edit-offre.component';
import { RhCandidatesCvsComponent } from './rh-candidates-cvs/rh-candidates-cvs.component';
import { RhQuizComponent } from './rh-quiz/rh-quiz.component';
import { RhQuizzListComponent } from './rh-quizz-list/rh-quizz-list.component';
import { RhQuizDetailsComponent } from './rh-quiz-details/rh-quiz-details.component';
import { RhQuizzResultsComponent } from './rh-quizz-results/rh-quizz-results.component';



@NgModule({
  imports: [
    CommonModule,
    RhRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatInputModule,
    MatListModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTableModule,
    NgbModule
  ],
  declarations: [RhProfilComponent, RhNavbarComponent, RhCreateOffreComponent, RhShowOffreComponent, RhOffreCandidaturesComponent, RhEditOffreComponent, RhCandidatesCvsComponent, RhQuizComponent, RhQuizzListComponent, RhQuizDetailsComponent, RhQuizzResultsComponent], 
})
export class RhModule { }

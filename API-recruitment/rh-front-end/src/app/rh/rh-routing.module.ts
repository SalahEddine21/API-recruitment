import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RhProfilComponent } from './rh-profil/rh-profil.component';
import { RhCreateOffreComponent } from './rh-create-offre/rh-create-offre.component';
import { RhShowOffreComponent } from './rh-show-offre/rh-show-offre.component';
import { RhOffreCandidaturesComponent } from './rh-offre-candidatures/rh-offre-candidatures.component';
import { RhEditOffreComponent } from './rh-edit-offre/rh-edit-offre.component';
import { RhCandidatesCvsComponent } from './rh-candidates-cvs/rh-candidates-cvs.component';
import { RhQuizComponent } from './rh-quiz/rh-quiz.component';
import { RhQuizzListComponent } from './rh-quizz-list/rh-quizz-list.component';
import { RhQuizDetailsComponent } from './rh-quiz-details/rh-quiz-details.component';
import { RhQuizzResultsComponent } from './rh-quizz-results/rh-quizz-results.component';

const routes: Routes = [
  {path:'rh/profil', component:RhProfilComponent},
  {path: 'rh/offre/create', component:RhCreateOffreComponent},
  {path: 'rh/offres', component:RhShowOffreComponent},
  {path: 'rh/offre/:id/candidatures', component:RhOffreCandidaturesComponent},
  {path : 'rh/offre/:id', component: RhEditOffreComponent},
  {path : 'rh/candidates', component: RhCandidatesCvsComponent},
  {path : 'rh/quiz', component: RhQuizComponent},
  {path: 'rh/quizz/list', component:RhQuizzListComponent},
  {path: 'rh/quiz/details/:id', component:RhQuizDetailsComponent},
  {path: 'rh/result/:candidateId/:quizzId', component: RhQuizzResultsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RhRoutingModule { }

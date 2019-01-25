import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CandidateProfilComponent } from './candidate-profil/candidate-profil.component';
import { CandidateShowOffresComponent } from './candidate-show-offres/candidate-show-offres.component';
import { CandidateOffreDetailsComponent } from './candidate-offre-details/candidate-offre-details.component';
import { CandidateApplicationsComponent } from './candidate-applications/candidate-applications.component';
import { CandidateTakeQuizzComponent } from './candidate-take-quizz/candidate-take-quizz.component';
import { CandidateQuizzComponent } from './candidate-quizz/candidate-quizz.component';

const routes: Routes = [
  {path : 'candidate/profil', component : CandidateProfilComponent},
  {path: 'offres', component: CandidateShowOffresComponent},
  {path: 'detail/offre/:id', component:CandidateOffreDetailsComponent},
  {path: 'candidate/applications', component:CandidateApplicationsComponent},
  {path: 'candidate/take/quizz/:token/:id', component:CandidateTakeQuizzComponent},
  {path: 'candidate/quizz', component:CandidateQuizzComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidateRoutingModule { }

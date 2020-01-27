import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'quiz',
        loadChildren: () => import('./quiz/quiz.module').then(m => m.QuizAdminQuizModule)
      },
      {
        path: 'personne',
        loadChildren: () => import('./personne/personne.module').then(m => m.QuizAdminPersonneModule)
      },
      {
        path: 'joueur',
        loadChildren: () => import('./joueur/joueur.module').then(m => m.QuizAdminJoueurModule)
      },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.QuizAdminAdminModule)
      },
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.QuizAdminQuestionModule)
      },
      {
        path: 'niveau',
        loadChildren: () => import('./niveau/niveau.module').then(m => m.QuizAdminNiveauModule)
      },
      {
        path: 'theme',
        loadChildren: () => import('./theme/theme.module').then(m => m.QuizAdminThemeModule)
      },
      {
        path: 'reponse',
        loadChildren: () => import('./reponse/reponse.module').then(m => m.QuizAdminReponseModule)
      },
      {
        path: 'media',
        loadChildren: () => import('./media/media.module').then(m => m.QuizAdminMediaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class QuizAdminEntityModule {}

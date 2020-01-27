import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QuizAdminSharedModule } from 'app/shared/shared.module';
import { JoueurComponent } from './joueur.component';
import { JoueurDetailComponent } from './joueur-detail.component';
import { JoueurUpdateComponent } from './joueur-update.component';
import { JoueurDeleteDialogComponent } from './joueur-delete-dialog.component';
import { joueurRoute } from './joueur.route';

@NgModule({
  imports: [QuizAdminSharedModule, RouterModule.forChild(joueurRoute)],
  declarations: [JoueurComponent, JoueurDetailComponent, JoueurUpdateComponent, JoueurDeleteDialogComponent],
  entryComponents: [JoueurDeleteDialogComponent]
})
export class QuizAdminJoueurModule {}

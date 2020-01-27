import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QuizAdminSharedModule } from 'app/shared/shared.module';
import { AdminComponent } from './admin.component';
import { AdminDetailComponent } from './admin-detail.component';
import { AdminUpdateComponent } from './admin-update.component';
import { AdminDeleteDialogComponent } from './admin-delete-dialog.component';
import { adminRoute } from './admin.route';

@NgModule({
  imports: [QuizAdminSharedModule, RouterModule.forChild(adminRoute)],
  declarations: [AdminComponent, AdminDetailComponent, AdminUpdateComponent, AdminDeleteDialogComponent],
  entryComponents: [AdminDeleteDialogComponent]
})
export class QuizAdminAdminModule {}

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { QuizAdminSharedModule } from 'app/shared/shared.module';
import { QuizAdminCoreModule } from 'app/core/core.module';
import { QuizAdminAppRoutingModule } from './app-routing.module';
import { QuizAdminHomeModule } from './home/home.module';
import { QuizAdminEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    QuizAdminSharedModule,
    QuizAdminCoreModule,
    QuizAdminHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    QuizAdminEntityModule,
    QuizAdminAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class QuizAdminAppModule {}

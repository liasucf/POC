import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QuizAdminTestModule } from '../../../test.module';
import { ReponseDetailComponent } from 'app/entities/reponse/reponse-detail.component';
import { Reponse } from 'app/shared/model/reponse.model';

describe('Component Tests', () => {
  describe('Reponse Management Detail Component', () => {
    let comp: ReponseDetailComponent;
    let fixture: ComponentFixture<ReponseDetailComponent>;
    const route = ({ data: of({ reponse: new Reponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QuizAdminTestModule],
        declarations: [ReponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reponse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

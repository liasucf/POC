import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPersonne, Personne } from 'app/shared/model/personne.model';
import { PersonneService } from './personne.service';
import { IQuiz } from 'app/shared/model/quiz.model';
import { QuizService } from 'app/entities/quiz/quiz.service';

@Component({
  selector: 'jhi-personne-update',
  templateUrl: './personne-update.component.html'
})
export class PersonneUpdateComponent implements OnInit {
  isSaving = false;

  quizzes: IQuiz[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    quizId: []
  });

  constructor(
    protected personneService: PersonneService,
    protected quizService: QuizService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personne }) => {
      this.updateForm(personne);

      this.quizService
        .query()
        .pipe(
          map((res: HttpResponse<IQuiz[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IQuiz[]) => (this.quizzes = resBody));
    });
  }

  updateForm(personne: IPersonne): void {
    this.editForm.patchValue({
      id: personne.id,
      nom: personne.nom,
      quizId: personne.quizId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personne = this.createFromForm();
    if (personne.id !== undefined) {
      this.subscribeToSaveResponse(this.personneService.update(personne));
    } else {
      this.subscribeToSaveResponse(this.personneService.create(personne));
    }
  }

  private createFromForm(): IPersonne {
    return {
      ...new Personne(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      quizId: this.editForm.get(['quizId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonne>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IQuiz): any {
    return item.id;
  }
}

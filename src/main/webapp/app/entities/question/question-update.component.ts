import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IMedia } from 'app/shared/model/media.model';
import { MediaService } from 'app/entities/media/media.service';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';
import { INiveau } from 'app/shared/model/niveau.model';
import { NiveauService } from 'app/entities/niveau/niveau.service';
import { IQuiz } from 'app/shared/model/quiz.model';
import { QuizService } from 'app/entities/quiz/quiz.service';

type SelectableEntity = IMedia | ITheme | INiveau | IQuiz;

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;

  media: IMedia[] = [];

  themes: ITheme[] = [];

  niveaus: INiveau[] = [];

  quizzes: IQuiz[] = [];

  editForm = this.fb.group({
    id: [],
    intitule: [],
    mediaId: [],
    themeId: [],
    niveauId: [],
    quizId: []
  });

  constructor(
    protected questionService: QuestionService,
    protected mediaService: MediaService,
    protected themeService: ThemeService,
    protected niveauService: NiveauService,
    protected quizService: QuizService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);

      this.mediaService
        .query()
        .pipe(
          map((res: HttpResponse<IMedia[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMedia[]) => (this.media = resBody));

      this.themeService
        .query()
        .pipe(
          map((res: HttpResponse<ITheme[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITheme[]) => (this.themes = resBody));

      this.niveauService
        .query()
        .pipe(
          map((res: HttpResponse<INiveau[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: INiveau[]) => (this.niveaus = resBody));

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

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      intitule: question.intitule,
      mediaId: question.mediaId,
      themeId: question.themeId,
      niveauId: question.niveauId,
      quizId: question.quizId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      intitule: this.editForm.get(['intitule'])!.value,
      mediaId: this.editForm.get(['mediaId'])!.value,
      themeId: this.editForm.get(['themeId'])!.value,
      niveauId: this.editForm.get(['niveauId'])!.value,
      quizId: this.editForm.get(['quizId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}

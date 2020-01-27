import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IQuiz, Quiz } from 'app/shared/model/quiz.model';
import { QuizService } from './quiz.service';
import { IAdmin } from 'app/shared/model/admin.model';
import { AdminService } from 'app/entities/admin/admin.service';

@Component({
  selector: 'jhi-quiz-update',
  templateUrl: './quiz-update.component.html'
})
export class QuizUpdateComponent implements OnInit {
  isSaving = false;

  admins: IAdmin[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    adminId: []
  });

  constructor(
    protected quizService: QuizService,
    protected adminService: AdminService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quiz }) => {
      this.updateForm(quiz);

      this.adminService
        .query()
        .pipe(
          map((res: HttpResponse<IAdmin[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAdmin[]) => (this.admins = resBody));
    });
  }

  updateForm(quiz: IQuiz): void {
    this.editForm.patchValue({
      id: quiz.id,
      nom: quiz.nom,
      adminId: quiz.adminId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quiz = this.createFromForm();
    if (quiz.id !== undefined) {
      this.subscribeToSaveResponse(this.quizService.update(quiz));
    } else {
      this.subscribeToSaveResponse(this.quizService.create(quiz));
    }
  }

  private createFromForm(): IQuiz {
    return {
      ...new Quiz(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      adminId: this.editForm.get(['adminId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuiz>>): void {
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

  trackById(index: number, item: IAdmin): any {
    return item.id;
  }
}

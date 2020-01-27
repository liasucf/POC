import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReponse, Reponse } from 'app/shared/model/reponse.model';
import { ReponseService } from './reponse.service';
import { IMedia } from 'app/shared/model/media.model';
import { MediaService } from 'app/entities/media/media.service';

@Component({
  selector: 'jhi-reponse-update',
  templateUrl: './reponse-update.component.html'
})
export class ReponseUpdateComponent implements OnInit {
  isSaving = false;

  media: IMedia[] = [];

  editForm = this.fb.group({
    id: [],
    intitule: [],
    isTrue: [],
    mediaId: []
  });

  constructor(
    protected reponseService: ReponseService,
    protected mediaService: MediaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reponse }) => {
      this.updateForm(reponse);

      this.mediaService
        .query()
        .pipe(
          map((res: HttpResponse<IMedia[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMedia[]) => (this.media = resBody));
    });
  }

  updateForm(reponse: IReponse): void {
    this.editForm.patchValue({
      id: reponse.id,
      intitule: reponse.intitule,
      isTrue: reponse.isTrue,
      mediaId: reponse.mediaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reponse = this.createFromForm();
    if (reponse.id !== undefined) {
      this.subscribeToSaveResponse(this.reponseService.update(reponse));
    } else {
      this.subscribeToSaveResponse(this.reponseService.create(reponse));
    }
  }

  private createFromForm(): IReponse {
    return {
      ...new Reponse(),
      id: this.editForm.get(['id'])!.value,
      intitule: this.editForm.get(['intitule'])!.value,
      isTrue: this.editForm.get(['isTrue'])!.value,
      mediaId: this.editForm.get(['mediaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReponse>>): void {
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

  trackById(index: number, item: IMedia): any {
    return item.id;
  }
}

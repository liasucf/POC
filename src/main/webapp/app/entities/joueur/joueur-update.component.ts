import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IJoueur, Joueur } from 'app/shared/model/joueur.model';
import { JoueurService } from './joueur.service';
import { IReponse } from 'app/shared/model/reponse.model';
import { ReponseService } from 'app/entities/reponse/reponse.service';

@Component({
  selector: 'jhi-joueur-update',
  templateUrl: './joueur-update.component.html'
})
export class JoueurUpdateComponent implements OnInit {
  isSaving = false;

  reponses: IReponse[] = [];

  editForm = this.fb.group({
    id: [],
    reponses: []
  });

  constructor(
    protected joueurService: JoueurService,
    protected reponseService: ReponseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ joueur }) => {
      this.updateForm(joueur);

      this.reponseService
        .query()
        .pipe(
          map((res: HttpResponse<IReponse[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IReponse[]) => (this.reponses = resBody));
    });
  }

  updateForm(joueur: IJoueur): void {
    this.editForm.patchValue({
      id: joueur.id,
      reponses: joueur.reponses
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const joueur = this.createFromForm();
    if (joueur.id !== undefined) {
      this.subscribeToSaveResponse(this.joueurService.update(joueur));
    } else {
      this.subscribeToSaveResponse(this.joueurService.create(joueur));
    }
  }

  private createFromForm(): IJoueur {
    return {
      ...new Joueur(),
      id: this.editForm.get(['id'])!.value,
      reponses: this.editForm.get(['reponses'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJoueur>>): void {
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

  trackById(index: number, item: IReponse): any {
    return item.id;
  }

  getSelected(selectedVals: IReponse[], option: IReponse): IReponse {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

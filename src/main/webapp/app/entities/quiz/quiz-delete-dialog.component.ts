import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuiz } from 'app/shared/model/quiz.model';
import { QuizService } from './quiz.service';

@Component({
  templateUrl: './quiz-delete-dialog.component.html'
})
export class QuizDeleteDialogComponent {
  quiz?: IQuiz;

  constructor(protected quizService: QuizService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quizService.delete(id).subscribe(() => {
      this.eventManager.broadcast('quizListModification');
      this.activeModal.close();
    });
  }
}

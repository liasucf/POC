import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuiz } from 'app/shared/model/quiz.model';

@Component({
  selector: 'jhi-quiz-detail',
  templateUrl: './quiz-detail.component.html'
})
export class QuizDetailComponent implements OnInit {
  quiz: IQuiz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quiz }) => {
      this.quiz = quiz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

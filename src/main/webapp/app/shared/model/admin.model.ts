import { IQuiz } from 'app/shared/model/quiz.model';

export interface IAdmin {
  id?: number;
  quizs?: IQuiz[];
}

export class Admin implements IAdmin {
  constructor(public id?: number, public quizs?: IQuiz[]) {}
}

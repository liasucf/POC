import { IQuestion } from 'app/shared/model/question.model';

export interface INiveau {
  id?: number;
  intitule?: string;
  questions?: IQuestion[];
}

export class Niveau implements INiveau {
  constructor(public id?: number, public intitule?: string, public questions?: IQuestion[]) {}
}

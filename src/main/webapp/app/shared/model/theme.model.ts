import { IQuestion } from 'app/shared/model/question.model';

export interface ITheme {
  id?: number;
  intitule?: string;
  couleur?: string;
  questions?: IQuestion[];
}

export class Theme implements ITheme {
  constructor(public id?: number, public intitule?: string, public couleur?: string, public questions?: IQuestion[]) {}
}

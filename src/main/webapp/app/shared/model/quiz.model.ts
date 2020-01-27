import { IPersonne } from 'app/shared/model/personne.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IQuiz {
  id?: number;
  nom?: string;
  personnes?: IPersonne[];
  questions?: IQuestion[];
  adminId?: number;
}

export class Quiz implements IQuiz {
  constructor(
    public id?: number,
    public nom?: string,
    public personnes?: IPersonne[],
    public questions?: IQuestion[],
    public adminId?: number
  ) {}
}

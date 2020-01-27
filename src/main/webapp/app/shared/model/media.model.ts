import { IReponse } from 'app/shared/model/reponse.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IMedia {
  id?: number;
  intitule?: string;
  type?: string;
  reponses?: IReponse[];
  questions?: IQuestion[];
}

export class Media implements IMedia {
  constructor(
    public id?: number,
    public intitule?: string,
    public type?: string,
    public reponses?: IReponse[],
    public questions?: IQuestion[]
  ) {}
}

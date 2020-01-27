import { IReponse } from 'app/shared/model/reponse.model';

export interface IJoueur {
  id?: number;
  reponses?: IReponse[];
}

export class Joueur implements IJoueur {
  constructor(public id?: number, public reponses?: IReponse[]) {}
}

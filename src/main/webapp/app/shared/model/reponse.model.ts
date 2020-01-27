import { IJoueur } from 'app/shared/model/joueur.model';

export interface IReponse {
  id?: number;
  intitule?: string;
  isTrue?: boolean;
  mediaId?: number;
  questions?: IJoueur[];
}

export class Reponse implements IReponse {
  constructor(
    public id?: number,
    public intitule?: string,
    public isTrue?: boolean,
    public mediaId?: number,
    public questions?: IJoueur[]
  ) {
    this.isTrue = this.isTrue || false;
  }
}

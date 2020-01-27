export interface IPersonne {
  id?: number;
  nom?: string;
  quizId?: number;
}

export class Personne implements IPersonne {
  constructor(public id?: number, public nom?: string, public quizId?: number) {}
}

export interface IQuestion {
  id?: number;
  intitule?: string;
  mediaId?: number;
  themeId?: number;
  niveauId?: number;
  quizId?: number;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public intitule?: string,
    public mediaId?: number,
    public themeId?: number,
    public niveauId?: number,
    public quizId?: number
  ) {}
}

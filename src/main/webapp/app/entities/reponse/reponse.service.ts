import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReponse } from 'app/shared/model/reponse.model';

type EntityResponseType = HttpResponse<IReponse>;
type EntityArrayResponseType = HttpResponse<IReponse[]>;

@Injectable({ providedIn: 'root' })
export class ReponseService {
  public resourceUrl = SERVER_API_URL + 'api/reponses';

  constructor(protected http: HttpClient) {}

  create(reponse: IReponse): Observable<EntityResponseType> {
    return this.http.post<IReponse>(this.resourceUrl, reponse, { observe: 'response' });
  }

  update(reponse: IReponse): Observable<EntityResponseType> {
    return this.http.put<IReponse>(this.resourceUrl, reponse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISales } from 'app/shared/model/sales.model';

type EntityResponseType = HttpResponse<ISales>;
type EntityArrayResponseType = HttpResponse<ISales[]>;

@Injectable({ providedIn: 'root' })
export class SalesService {
    public resourceUrl = SERVER_API_URL + 'api/sales';

    constructor(protected http: HttpClient) {}

    create(sales: ISales): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sales);
        return this.http
            .post<ISales>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(sales: ISales): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sales);
        return this.http
            .put<ISales>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISales>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISales[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(sales: ISales): ISales {
        const copy: ISales = Object.assign({}, sales, {
            date: sales.date != null && sales.date.isValid() ? sales.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((sales: ISales) => {
                sales.date = sales.date != null ? moment(sales.date) : null;
            });
        }
        return res;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISales } from 'app/shared/model/sales.model';
import { SalesService } from './sales.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-sales-update',
    templateUrl: './sales-update.component.html'
})
export class SalesUpdateComponent implements OnInit {
    sales: ISales;
    isSaving: boolean;

    books: IBook[];

    clients: IClient[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected salesService: SalesService,
        protected bookService: BookService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sales }) => {
            this.sales = sales;
            this.date = this.sales.date != null ? this.sales.date.format(DATE_TIME_FORMAT) : null;
        });
        this.bookService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBook[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBook[]>) => response.body)
            )
            .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.clientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClient[]>) => response.body)
            )
            .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.sales.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.sales.id !== undefined) {
            this.subscribeToSaveResponse(this.salesService.update(this.sales));
        } else {
            this.subscribeToSaveResponse(this.salesService.create(this.sales));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISales>>) {
        result.subscribe((res: HttpResponse<ISales>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBookById(index: number, item: IBook) {
        return item.id;
    }

    trackClientById(index: number, item: IClient) {
        return item.id;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBasket } from 'app/shared/model/basket.model';
import { BasketService } from './basket.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-basket-update',
    templateUrl: './basket-update.component.html'
})
export class BasketUpdateComponent implements OnInit {
    basket: IBasket;
    isSaving: boolean;

    books: IBook[];

    clients: IClient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected basketService: BasketService,
        protected bookService: BookService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ basket }) => {
            this.basket = basket;
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
        if (this.basket.id !== undefined) {
            this.subscribeToSaveResponse(this.basketService.update(this.basket));
        } else {
            this.subscribeToSaveResponse(this.basketService.create(this.basket));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBasket>>) {
        result.subscribe((res: HttpResponse<IBasket>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

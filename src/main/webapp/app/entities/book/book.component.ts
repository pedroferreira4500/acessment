import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription, fromEvent, of, Subject } from 'rxjs';
import { filter, map, delay, tap, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { IBook } from '../../shared/model/book.model';
import { AccountService } from '../../core';
import { BookService } from './book.service';
import { BasketService } from '../basket/basket.service';
import { IBasket } from '../../shared/model/basket.model';

@Component({
    selector: 'jhi-book',
    templateUrl: './book.component.html',
    providers: []
})
export class BookComponent implements OnInit, OnDestroy {
    baskets: IBasket[];
    books: IBook[];
    currentAccount: any;
    eventSubscriber: Subscription;
    public searchText: string;
    results: Object;
    searchTerm$ = new Subject<string>();

    constructor(
        protected bookService: BookService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected basketService: BasketService
    ) {}

    addToBasket(book: IBook) {
        // console.log( this.basketService.create({ book: book, client: this.currentAccount }) );
    }

    loadAll() {
        this.bookService
            .query()
            .pipe(
                filter((res: HttpResponse<IBook[]>) => res.ok),
                map((res: HttpResponse<IBook[]>) => res.body)
            )
            .subscribe(
                (res: IBook[]) => {
                    this.books = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    searchTitle(title) {
        this.bookService
            .search(title)
            .pipe(
                filter((res: HttpResponse<IBook[]>) => res.ok),
                map((res: HttpResponse<IBook[]>) => res.body)
            )
            .subscribe(
                (res: IBook[]) => {
                    this.books = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBook) {
        return item.id;
    }

    registerChangeInBooks() {
        this.eventSubscriber = this.eventManager.subscribe('bookListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

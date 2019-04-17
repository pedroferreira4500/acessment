import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBasket } from 'app/shared/model/basket.model';
import { AccountService } from 'app/core';
import { BasketService } from './basket.service';

@Component({
    selector: 'jhi-basket',
    templateUrl: './basket.component.html'
})
export class BasketComponent implements OnInit, OnDestroy {
    baskets: IBasket[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected basketService: BasketService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.basketService
            .query()
            .pipe(
                filter((res: HttpResponse<IBasket[]>) => res.ok),
                map((res: HttpResponse<IBasket[]>) => res.body)
            )
            .subscribe(
                (res: IBasket[]) => {
                    this.baskets = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBaskets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBasket) {
        return item.id;
    }

    registerChangeInBaskets() {
        this.eventSubscriber = this.eventManager.subscribe('basketListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

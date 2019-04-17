import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISales } from 'app/shared/model/sales.model';
import { AccountService } from 'app/core';
import { SalesService } from './sales.service';

@Component({
    selector: 'jhi-sales',
    templateUrl: './sales.component.html'
})
export class SalesComponent implements OnInit, OnDestroy {
    sales: ISales[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected salesService: SalesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.salesService
            .query()
            .pipe(
                filter((res: HttpResponse<ISales[]>) => res.ok),
                map((res: HttpResponse<ISales[]>) => res.body)
            )
            .subscribe(
                (res: ISales[]) => {
                    this.sales = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSales();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISales) {
        return item.id;
    }

    registerChangeInSales() {
        this.eventSubscriber = this.eventManager.subscribe('salesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

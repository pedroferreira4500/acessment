import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBasket } from 'app/shared/model/basket.model';
import { AccountService } from 'app/core';
import { BasketService } from './basket.service';

@Component({
  selector: 'jhi-basket-client',
  templateUrl: './basket-client.component.html',
  styles: []
})
export class BasketClientComponent implements OnInit {
  basket: IBasket;
  currentAccount: any;
  eventSubscriber: Subscription;
  clientId: number;
  constructor(
      protected basketService: BasketService,
      protected jhiAlertService: JhiAlertService,
      protected eventManager: JhiEventManager,
      protected accountService: AccountService
  ) {}

  loadAll() {
    this.basketService
        .searchClient(this.clientId)
        .pipe(
            filter((res: HttpResponse<IBasket>) => res.ok),
            map((res: HttpResponse<IBasket>) => res.body)
        )
        .subscribe(
            (res: IBasket) => {
                this.basket = res;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
}
  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
  });
}

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
}

  }

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Basket } from 'app/shared/model/basket.model';
import { BasketService } from './basket.service';
import { BasketClientComponent } from './basket-client.component';
import { BasketComponent } from './basket.component';
import { BasketDetailComponent } from './basket-detail.component';
import { BasketUpdateComponent } from './basket-update.component';
import { BasketDeletePopupComponent } from './basket-delete-dialog.component';
import { IBasket } from 'app/shared/model/basket.model';

@Injectable({ providedIn: 'root' })
export class BasketResolve implements Resolve<IBasket> {
    constructor(private service: BasketService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBasket> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Basket>) => response.ok),
                map((basket: HttpResponse<Basket>) => basket.body)
            );
        }
        return of(new Basket());
    }
}

export const basketRoute: Routes = [
    {
        path: '',
        component: BasketComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Baskets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mybasket',
        component: BasketClientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shopping Cart'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BasketDetailComponent,
        resolve: {
            basket: BasketResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Baskets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BasketUpdateComponent,
        resolve: {
            basket: BasketResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Baskets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BasketUpdateComponent,
        resolve: {
            basket: BasketResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Baskets'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const basketPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BasketDeletePopupComponent,
        resolve: {
            basket: BasketResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Baskets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sales } from 'app/shared/model/sales.model';
import { SalesService } from './sales.service';
import { SalesComponent } from './sales.component';
import { SalesDetailComponent } from './sales-detail.component';
import { SalesUpdateComponent } from './sales-update.component';
import { SalesDeletePopupComponent } from './sales-delete-dialog.component';
import { ISales } from 'app/shared/model/sales.model';

@Injectable({ providedIn: 'root' })
export class SalesResolve implements Resolve<ISales> {
    constructor(private service: SalesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISales> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Sales>) => response.ok),
                map((sales: HttpResponse<Sales>) => sales.body)
            );
        }
        return of(new Sales());
    }
}

export const salesRoute: Routes = [
    {
        path: '',
        component: SalesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalesDetailComponent,
        resolve: {
            sales: SalesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalesUpdateComponent,
        resolve: {
            sales: SalesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sales'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalesUpdateComponent,
        resolve: {
            sales: SalesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sales'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalesDeletePopupComponent,
        resolve: {
            sales: SalesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sales'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

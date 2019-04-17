import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared';
import {
    BasketComponent,
    BasketDetailComponent,
    BasketUpdateComponent,
    BasketDeletePopupComponent,
    BasketDeleteDialogComponent,
    basketRoute,
    basketPopupRoute
} from './';

const ENTITY_STATES = [...basketRoute, ...basketPopupRoute];

@NgModule({
    imports: [LibrarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BasketComponent, BasketDetailComponent, BasketUpdateComponent, BasketDeleteDialogComponent, BasketDeletePopupComponent],
    entryComponents: [BasketComponent, BasketUpdateComponent, BasketDeleteDialogComponent, BasketDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibraryBasketModule {}
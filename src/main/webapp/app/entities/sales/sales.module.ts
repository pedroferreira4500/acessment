import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared';
import {
    SalesComponent,
    SalesDetailComponent,
    SalesUpdateComponent,
    SalesDeletePopupComponent,
    SalesDeleteDialogComponent,
    salesRoute,
    salesPopupRoute
} from './';

const ENTITY_STATES = [...salesRoute, ...salesPopupRoute];

@NgModule({
    imports: [LibrarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SalesComponent, SalesDetailComponent, SalesUpdateComponent, SalesDeleteDialogComponent, SalesDeletePopupComponent],
    entryComponents: [SalesComponent, SalesUpdateComponent, SalesDeleteDialogComponent, SalesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibrarySalesModule {}

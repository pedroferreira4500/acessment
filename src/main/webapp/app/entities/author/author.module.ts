import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared';
import {
    AuthorComponent,
    AuthorDetailComponent,
    AuthorUpdateComponent,
    AuthorDeletePopupComponent,
    AuthorDeleteDialogComponent,
    authorRoute,
    authorPopupRoute
} from './';
import { AuthorBooksComponent } from './author-books.component';

const ENTITY_STATES = [...authorRoute, ...authorPopupRoute];

@NgModule({
    imports: [LibrarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AuthorComponent,
        AuthorDetailComponent,
        AuthorUpdateComponent,
        AuthorDeleteDialogComponent,
        AuthorDeletePopupComponent,
        AuthorBooksComponent
    ],
    entryComponents: [AuthorComponent, AuthorUpdateComponent, AuthorDeleteDialogComponent, AuthorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibraryAuthorModule {}

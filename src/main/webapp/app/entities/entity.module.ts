import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MyBasketComponent } from './my-basket/my-basket.component';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'book',
                loadChildren: './book/book.module#LibraryBookModule'
            },
            {
                path: 'author',
                loadChildren: './author/author.module#LibraryAuthorModule'
            },
            {
                path: 'client',
                loadChildren: './client/client.module#LibraryClientModule'
            },
            {
                path: 'basket',
                loadChildren: './basket/basket.module#LibraryBasketModule'
            },
            {
                path: 'sales',
                loadChildren: './sales/sales.module#LibrarySalesModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [MyBasketComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibraryEntityModule {}

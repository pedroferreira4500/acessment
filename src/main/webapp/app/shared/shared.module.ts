import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { LibrarySharedLibsModule, LibrarySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, HasNoAuthorityDirective } from './';

@NgModule({
    imports: [LibrarySharedLibsModule, LibrarySharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, HasNoAuthorityDirective ],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [LibrarySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, HasNoAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibrarySharedModule {
    static forRoot() {
        return {
            ngModule: LibrarySharedModule
        };
    }
}

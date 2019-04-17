/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LibraryTestModule } from '../../../test.module';
import { SalesComponent } from 'app/entities/sales/sales.component';
import { SalesService } from 'app/entities/sales/sales.service';
import { Sales } from 'app/shared/model/sales.model';

describe('Component Tests', () => {
    describe('Sales Management Component', () => {
        let comp: SalesComponent;
        let fixture: ComponentFixture<SalesComponent>;
        let service: SalesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LibraryTestModule],
                declarations: [SalesComponent],
                providers: []
            })
                .overrideTemplate(SalesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Sales(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

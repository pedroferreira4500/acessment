/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { BasketDetailComponent } from 'app/entities/basket/basket-detail.component';
import { Basket } from 'app/shared/model/basket.model';

describe('Component Tests', () => {
    describe('Basket Management Detail Component', () => {
        let comp: BasketDetailComponent;
        let fixture: ComponentFixture<BasketDetailComponent>;
        const route = ({ data: of({ basket: new Basket(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LibraryTestModule],
                declarations: [BasketDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BasketDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BasketDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.basket).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

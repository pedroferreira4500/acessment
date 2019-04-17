import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISales } from 'app/shared/model/sales.model';
import { SalesService } from './sales.service';

@Component({
    selector: 'jhi-sales-delete-dialog',
    templateUrl: './sales-delete-dialog.component.html'
})
export class SalesDeleteDialogComponent {
    sales: ISales;

    constructor(protected salesService: SalesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salesListModification',
                content: 'Deleted an sales'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sales-delete-popup',
    template: ''
})
export class SalesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sales }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sales = sales;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sales', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sales', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

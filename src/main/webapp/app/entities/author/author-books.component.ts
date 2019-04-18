import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAuthor, Author } from 'app/shared/model/author.model';
import { IBook } from '../../shared/model/book.model';
import { BookService } from '../book';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription, fromEvent, of, Subject } from 'rxjs';
import { filter, map, delay, tap, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-author-books',
  templateUrl: './author-books.component.html',
  styles: []
})
export class AuthorBooksComponent implements OnInit {
  books: IBook[];
  author: IAuthor;
  authorName: string;

  constructor(protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected bookService: BookService
    ) {}

  loadAll() {
    this.bookService
    .searchAuthor(this.authorName)
    .pipe(
      filter((res: HttpResponse<IBook[]>) => res.ok),
      map((res: HttpResponse<IBook[]>) => res.body)
  )
  .subscribe(
      (res: IBook[]) => {
          this.books = res;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
  );
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ author }) => {
      this.author = author;
      this.authorName = author.name;
      this.loadAll();
    });
  }

  previousState() {
    window.history.back();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
}
}

import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';
import { IClient } from 'app/shared/model/client.model';

export interface ISales {
    id?: number;
    date?: Moment;
    book?: IBook;
    client?: IClient;
}

export class Sales implements ISales {
    constructor(public id?: number, public date?: Moment, public book?: IBook, public client?: IClient) {}
}

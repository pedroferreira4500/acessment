import { IBook } from 'app/shared/model/book.model';
import { IClient } from 'app/shared/model/client.model';

export interface IBasket {
    id?: number;
    book?: IBook;
    client?: IClient;
}

export class Basket implements IBasket {
    constructor(public id?: number, public book?: IBook, public client?: IClient) {}
}

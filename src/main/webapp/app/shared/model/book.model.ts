import { IBasket } from '../../shared/model/basket.model';
import { ISales } from '../../shared/model/sales.model';
import { IAuthor } from '../../shared/model/author.model';

export interface IBook {
    id?: number;
    name?: string;
    price?: number;
    stock?: number;
    baskets?: IBasket[];
    sales?: ISales[];
    writer?: IAuthor;
}

export class Book implements IBook {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public stock?: number,
        public baskets?: IBasket[],
        public sales?: ISales[],
        public writer?: IAuthor
    ) {}
}

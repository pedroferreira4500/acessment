import { IBasket } from 'app/shared/model/basket.model';
import { ISales } from 'app/shared/model/sales.model';

export interface IClient {
    id?: number;
    name?: string;
    email?: string;
    password?: string;
    baskets?: IBasket[];
    sales?: ISales[];
}

export class Client implements IClient {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public password?: string,
        public baskets?: IBasket[],
        public sales?: ISales[]
    ) {}
}

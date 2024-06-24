import { ProductDTO } from "./productDTO";

export interface CartDTO{
    totalAmount: number;
    cartItems:ProductDTO[]
}
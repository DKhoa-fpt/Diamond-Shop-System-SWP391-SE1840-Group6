import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  public cartItemList : any =[];
  public productList = new BehaviorSubject<any>([]);
  public search = new BehaviorSubject<string>("");

  public cartItems = new BehaviorSubject<any[]>(null);
  cartItems$ = this.cartItems.asObservable();
  public totalProductInCart = new BehaviorSubject<number>(null);
  totalProductInCart$ = this.totalProductInCart.asObservable();
  public totalPrice = new BehaviorSubject<number>(null);
  totalPrice$ = this.totalPrice.asObservable();
  httpOptions: any;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private http: HttpClient,) {
    this.httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
        "Authorization": `Bearer ${JSON.parse(localStorage.getItem('user')!)?.accessToken}`,
      }),
      "Access-Control-Allow-Origin": `${environment.apiUrl}`,
      "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",

    };
  }
  getProducts(){
    return this.productList.asObservable();
  }

  setProduct(product : any){
    this.cartItemList.push(...product);
    this.productList.next(product);
  }
  addtoCart(product : any){
    product.quantity = 1
    console.log(product.quantity)
    this.cartItemList.push(product);
    this.productList.next(this.cartItemList);
    this.getTotalPrice();
    console.log(this.cartItemList)
  }
  getInforCart(): String{
    let info = "";
    this.cartItemList.map((a:any)=>{
      info += '%'+a.name +'-'+a.quantity+'-'+a.price+'%';
    })
    return info
  }
  getTotalPrice() : number{
    let grandTotal = 0;
    this.cartItemList.map((a:any)=>{
      grandTotal += a.price*a.quantity;
    })
    return grandTotal;
  }
  getTotalPriceV2(products: any[]) : number{
    let grandTotal = 0;
    products.map((a:any)=>{
      grandTotal += a.quantity_number*a.price_items;
    })
    return grandTotal;
  }
  getTotalProduct(products: any[]): number {
    let grandTotal = 0;
    products.map((a:any)=>{
      grandTotal += a.quantity_number;
    })
    return grandTotal;
  }
  removeCartItem(request: any) :Observable<any>{
    return this.http.post<any[]>(`${environment.apiUrl}/cart/delete`,request,this.httpOptions);
  }
  removeAllCart(){
    this.cartItemList = []
    this.productList.next(this.cartItemList);
  }
  addProductToCard(request:any): Observable<any>{
    return this.http.post(`${environment.apiUrl}/cart/add_card`,request,this.httpOptions);
  }
  getProductInCart(request:any): Observable<any>{
    return this.http.post<any[]>(`${environment.apiUrl}/cart/list`,request,this.httpOptions);
  }
  getProductInCartV2(request:any,token:any): Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      }),
      "Access-Control-Allow-Origin": `${environment.apiUrl}`,
      "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",

    };
    return this.http.post<any[]>(`${environment.apiUrl}/cart/list`,request,httpOptions);
  }
  updateProductToCard(request:any): Observable<any>{
    return this.http.post(`${environment.apiUrl}/cart/update`,request,this.httpOptions);
  }
}

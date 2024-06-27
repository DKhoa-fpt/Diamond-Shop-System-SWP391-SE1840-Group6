import {Injectable} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrderService{
  httpOptions: any;
  httpOptions2: any;
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
    this.httpOptions2 = {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
        "Authorization": `Bearer ${JSON.parse(localStorage.getItem('user')!)?.accessToken}`,
      }),
      "Access-Control-Allow-Origin": `${environment.apiPayment}`,
      "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",

    };
  }
  addOrder(request: any):Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/order/add_order`,request,this.httpOptions);
  }
  getAllOrder(request: any):Observable<any> {
    return this.http.post<any[]>(`${environment.apiUrl}/order/list`,request,this.httpOptions);
  }
  getCallPaymentVnPay(request: any): Observable<any> {
    return this.http.post<any>(`${environment.apiPayment}/v1/payment/vnp/create`,request,this.httpOptions2);
  }
  getCallPaymentStrip(request: any): Observable<any> {
    return this.http.post<any>(`${environment.apiPayment}/v1/payment/stripe/create`,request,this.httpOptions2);
  }
  getDetailOrder(request): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/order/detail`,request,this.httpOptions);
  }
  useVoucher( request: any) : Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/shop/coupon/use`,request,this.httpOptions)
  }
  checkVoucher( request: any) : Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/shop/coupon/check?code=${request.id}`,this.httpOptions)
  }
}

import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../../service/product.service";
import {ToastrService} from "ngx-toastr";
import {NumberService} from "../../service/number.service";
import {AccountService} from "../../auth/services/account.service";
import {AuthGoogleService} from "../../../core/shared/auth-google.service";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  isLoginUser:boolean = false;
  listProduct:any[]
  rings: any[]
  famousRing : any

  constructor(
    private googleService: AuthGoogleService,
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private toastrService: ToastrService,
    private numberFormat: NumberService,
    private accountServie: AccountService,
    private cartService: CartService) {
  }

  ngOnInit(): void {
    if(this.googleService.getIdToken() || localStorage.getItem("loginWithGoogle")){
      if(localStorage.getItem("loginWithGoogle")){
        localStorage.removeItem("loginWithGoogle");
        location.reload();
      }
      const body = {token:this.googleService.getIdToken()}
      this.accountServie.loginWithGoogle(body).subscribe((res) =>{
        this.accountServie.userSubject.next(res);
        localStorage.setItem("user",JSON.stringify(res))
        this.getProductCartV2(res.accessToken);
        this.isLoginUser = true;
      },error => {

      });
    } else {
      this.isLoginUser = localStorage.getItem("user") != null;
      if(this.isLoginUser) {
        this.getProductCart();
      }
    }
    this.getProducts();
  }

  redirectLogin() {
    const returnUrl = this.route.snapshot.queryParams['/login-v3'] || '/login-v3';
    this.router.navigateByUrl(returnUrl);

  }
  redirectProfile() {
    const returnUrl = this.route.snapshot.queryParams['/user-profile'] || '/user-profile';
    this.router.navigateByUrl(returnUrl);

  }
  convertNumber(number){
    return this.numberFormat.convertNumber(number);
  }
  goToProductDetail(id:number) {
    const returnUrl = this.route.snapshot.queryParams[`/product?id=${id}`] || `/product?id=${id}`;
    this.router.navigateByUrl(returnUrl);
  }

  getProducts() {
    let request = {
      jewelry_type_id:1,
      limit:4,
      offset:0,
      requestId:''
    }
    let requestOfRings = {
      jewelry_type_id:6,
      limit:2,
      offset:0,
      requestId:''
    }
    this.productService.getProducts(request).subscribe((res) => {
      this.listProduct = res.data;
    }, error => {
      this.toastrService.error("Error get famous products");
    });
    this.productService.getProducts(requestOfRings).subscribe((res) => {
      this.rings = res.data;
    }, error => {
      this.toastrService.error("Error get famous rings");
    });
    this.productService.getProductDetail(62).subscribe((res) => {
      this.famousRing = res.data;
    }, error => {
      this.toastrService.error("Error get famous ring");
    })

      }
  getProductCart() {
    const request = {
      customer_id : null
    }
    this.cartService.getProductInCart(request).subscribe((res) =>{
      this.cartService.cartItems.next(res?.data);
      this.cartService.totalProductInCart.next(this.cartService.getTotalProduct(res?.data));
      // this.cartService.totalPrice.next(this.cartService.getTotalPriceV2(res?.data));
    }, error => {

    })
  }
  getProductCartV2(token:any) {
    const request = {
      customer_id : null
    }
    this.cartService.getProductInCartV2(request,token).subscribe((res) =>{
      this.cartService.cartItems.next(res?.data);
      this.cartService.totalProductInCart.next(this.cartService.getTotalProduct(res?.data));
      // this.cartService.totalPrice.next(this.cartService.getTotalPriceV2(res?.data));
    }, error => {

    })
  }
  addProductCart(product:any){
    if(!this.isLoginUser) {
      this.toastrService.error("You are login first");
      return;
    }
    const request = {
      jewelry_id : product?.id_jewelry,
      quantity : 1
    }
    this.cartService.addProductToCard(request).subscribe((res) =>{
      this.toastrService.success("Add product to cart success");
      this.getProductCart();
    }, error => {
      this.toastrService.error("Add product to cart fail");
    });
  }

}

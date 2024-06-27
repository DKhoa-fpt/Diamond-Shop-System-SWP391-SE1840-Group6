import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {ProductService} from "../../../modules/service/product.service";
import {ToastrService} from "ngx-toastr";
import {NumberService} from "../../../modules/service/number.service";
import {AccountService} from "../../../modules/auth/services/account.service";
import {AuthGoogleService} from "../../shared/auth-google.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  isLoginUser:boolean = false;
  listProduct:any[]

  constructor(private route: ActivatedRoute,
              private router: Router,
              private productService: ProductService,
              private toastrService: ToastrService,
              private numberFormat: NumberService,
              private accountServie: AccountService,
              private googleService :AuthGoogleService) {
    if(googleService.getIdToken()){
      const body = {token:googleService.getIdToken()}
      accountServie.loginWithGoogle(body).subscribe((res) =>{
        console.log(res);
        localStorage.setItem("user",JSON.stringify(res))
      },error => {

      });
    }
  }

  ngOnInit(): void {
        this.isLoginUser = localStorage.getItem("user") != null;
        this.getProducts();
        console.log(localStorage.getItem("user"),this.isLoginUser);
    }

  redirectLogin() {
    const returnUrl = this.route.snapshot.queryParams['/login-v3'] || '/login-v3';
    this.router.navigateByUrl(returnUrl).then(r =>{});

  }
  redirectProfile() {
    const returnUrl = this.route.snapshot.queryParams['/user-profile'] || '/user-profile';
    this.router.navigateByUrl(returnUrl).then(r =>{});

  }
  convertNumber(number){
    return this.numberFormat.convertNumber(number);
  }
  goToProductDetail(id:number) {
    console.log(id);
    const returnUrl = this.route.snapshot.queryParams[`/ecommerce?id=${id}`] || `/ecommerce?id=${id}`;
    this.router.navigateByUrl(returnUrl).then(r =>{});
  }

  getProducts() {
    let request = {
      jewelry_type_id:1,
      limit:10,
      offset:0,
      requestId:''
    }
    this.productService.getProducts(request).subscribe((res) => {
      this.listProduct = res.data;

    }, error => {


    });
  }
}

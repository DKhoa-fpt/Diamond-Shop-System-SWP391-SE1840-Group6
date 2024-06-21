import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../../service/product.service";
import {ToastrService} from "ngx-toastr";
import {NumberService} from "../../service/number.service";
import {AccountService} from "../services/account.service";
import {AuthGoogleService} from "../../../core/shared/auth-google.service";

@Component({
  selector: 'app-login-success',
  templateUrl: './login-success.component.html',
  styleUrls: ['./login-success.component.css']
})
export class LoginSuccessComponent {
  isLoginUser:boolean = false;

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
        localStorage.setItem("user",JSON.stringify(res))
      },error => {

      });
    }
  }

  ngOnInit(): void {
    this.isLoginUser = localStorage.getItem("user") != null;
  }

}

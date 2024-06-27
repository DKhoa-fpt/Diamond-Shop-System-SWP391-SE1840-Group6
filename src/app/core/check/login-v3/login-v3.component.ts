import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../../modules/auth/services/account.service";
import {first} from "rxjs/operators";
import {AuthGoogleService} from "../../shared/auth-google.service";
import {ToastrService} from "ngx-toastr";

declare function handleAuthClick() : any;
@Component({
  selector: 'app-login-v3',
  templateUrl: './login-v3.component.html',
  styleUrls: ['./login-v3.component.css']
})
export class LoginV3Component {
  form!: FormGroup;
  loading = false;
  submitted = false;
  loginForm !:FormGroup;
  registerForm !:FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private authGoogleService: AuthGoogleService,
    private toastrService: ToastrService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      rePassword: ['', Validators.required]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }
  get callLogin() { return this.loginForm.controls; }
  get callRegister() { return this.registerForm.controls; }

  onSingIn() {
    this.submitted = true;

    // reset alerts on submit

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    console.log(this.callLogin['email'].value, this.callLogin['password'].value);
    this.accountService.login(this.callLogin['email'].value, this.callLogin['password'].value)
      .pipe(first())
      .subscribe({
        next: () => {
          this.toastrService.success("login sucescss");
          const returnUrl = this.route.snapshot.queryParams['/home-page'] || '/home-page';
          this.router.navigateByUrl(returnUrl).then(r =>{});
        },
        error: error => {
          console.log(error);
          this.toastrService.error(error.error?.error);
          console.log("login error");
          this.loading = false;
        }
      });
  }
  signInWithGoogle(): void {
    this.authGoogleService.login();
    this.toastrService.success("login sucescss");
  }

  moveToForgetPassword(){
    this.router.navigate(['reset-password']);
  }


}

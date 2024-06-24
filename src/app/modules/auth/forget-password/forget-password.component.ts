import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../services/account.service";
import {AuthGoogleService} from "../../../core/shared/auth-google.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent {
  form!: FormGroup;
  formForget!: FormGroup;

  user : any;
  otpConfig = {
    length: 6,
    inputStyles: {
      'width': '60px',
      'height': '60px',
      'margin': '0 10px'
    },
    containerStyles: {
      'display': 'flex',
      'justify-content': 'center'
    }
  };
  isDisableButton:boolean = false;
  isDisableForgetButton:boolean = false;
  isShowFormReset: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private accountService: AccountService,
              private authGoogleService: AuthGoogleService,
              private toastrService: ToastrService) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      password: ['', Validators.required],
      rePassword: ['', Validators.required],
      otp:[''],
    });
    this.formForget = this.formBuilder.group({
      email: ['', Validators.required]
    });
  }

  get f() {
    return this.form.controls;
  }
  onForgetPassWord() {
    if (this.formForget.invalid) {
      this.toastrService.error("Xem lại thông tin vừa nhập");
      return;
    }
    this.isDisableForgetButton = true;
    this.accountService.getOtpFogetPassWord(this.formForget.controls['email'].value).subscribe((res) => {
      this.toastrService.success(`Gửi mã Otp đến ${this.formForget.controls['email'].value} thành công`)
      this.onOpenModal("otp");
    }, error => {
      this.toastrService.error("Không gửi được mã OTP")
      this.isDisableButton = false;
    });
  }
  callReset() {
    this.isShowFormReset = true;
  }

  callForgetPassWord() {
    const user = {
      email:this.formForget.controls['email'].value,
      password: this.form.controls['password'].value,
      otp: this.form.controls['otp'].value,
    };
    this.accountService.changePassWord(user).subscribe((res) => {
        this.toastrService.success("Reset mật khẩu thành công");
        this.isDisableButton = false;
        const returnUrl = this.route.snapshot.queryParams['/my-login'] || '/my-login';
        this.router.navigateByUrl(returnUrl);
      },
      error => {
        if (error.data) {
          this.toastrService.error(error.data);
        } else {
          this.toastrService.error("Reset mật khẩu thất bại");
        }

        this.isDisableButton = false;
      })
  }
  public onOpenModal( mode: string): void{
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'otp') {
      button.setAttribute('data-target', '#otpModal');
    }
    container.appendChild(button);
    button.click();
  }
}

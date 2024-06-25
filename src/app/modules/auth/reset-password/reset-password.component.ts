import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../services/account.service";
import {AuthGoogleService} from "../../../core/shared/auth-google.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
  form!: FormGroup;
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

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private accountService: AccountService,
              private authGoogleService: AuthGoogleService,
              private toastrService: ToastrService) {
    this.callUserDetail();
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      password: ['', Validators.required],
      rePassword: ['', Validators.required],
      otp:[''],

    });
  }

  get f() {
    return this.form.controls;
  }

  onResetPassWord() {
    if (this.form.invalid) {
      this.toastrService.error("Xem lại thông tin vừa nhập");
      return;
    }
    if (this.form.controls['password'].value !== this.form.controls['rePassword'].value) {
      this.toastrService.error("Mật khẩu nhập không khớp");
      return;
    }
    this.isDisableButton = true;
    this.accountService.getOtpFogetPassWord(this.user?.email).subscribe((res) => {
      console.log("checkkkk");
      this.toastrService.success(`Gửi mã Otp đến ${this.user?.email} thành công`)
      this.onOpenModal("otp");
    }, error => {
      this.toastrService.error("Không gửi được mã OTP")
      this.isDisableButton = false;
    });

  }

  callReset() {
    const user = {
      email : this.user?.email,
      password: this.form.controls['password'].value, //this.form['']
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
      button.setAttribute('data-target', '#otpModal2');
    }
    container.appendChild(button);
    button.click();
  }
  callUserDetail() {
    this.accountService.getUser().subscribe((res) => {
         this.user = res?.data;
      },
      error => {
        this.toastrService.error("lỗi lấy thông tin user");
        console.log(error);
      });

  }

}

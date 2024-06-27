import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../modules/auth/services/account.service";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {FileUploadService} from "../../../modules/service/file-upload.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  @ViewChild('closeButton') closeButton:ElementRef;
  form!: FormGroup;
  urlImg : string = '';
  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';
  preview = '';

  imageInfos?: Observable<any>;

  constructor(private accountService: AccountService,
              private toastrService: ToastrService,
              private route: ActivatedRoute,
              private router: Router,
              private uploadService: FileUploadService,
              ) {
    this.form = new FormGroup(
      {
        'email': new FormControl('', Validators.required),
        'full_name': new FormControl('',),
        'phone_number': new FormControl('',),
        'age': new FormControl('',),
        'date_of_birth': new FormControl('', ),
        'province': new FormControl('', ),
        'district': new FormControl('', ),
        'city': new FormControl('', ),
        'ward': new FormControl('', ),
        'extra': new FormControl('', ),
      }

    );
  }
  get f(){
    return this.form.controls;
  }

  ngOnInit(): void {
    this.callUserDetail();

  }

  onUpdate() {
    console.log(this.form);
    const user ={
      phoneNumber: this.form.controls['phone_number'].value,
      password: '123456' , //this.form['']
      fullName: this.form.controls['full_name'].value,
      isMale: false, //this.form['']
      dateOfBirth: this.form.controls['date_of_birth'].value,
      province: this.form.controls['province'].value,
      age: this.form.controls['age'].value,
      district: this.form.controls['district'].value,
      city: this.form.controls['city'].value,
      ward: this.form.controls['ward'].value,
      extra: this.form.controls['extra'].value,

  } ;
    console.log(user);
    this.accountService.updateUser(user).subscribe((res) =>{
        this.toastrService.success("Cập nhập thông tin userthành công");
        const returnUrl = this.route.snapshot.queryParams['/user-profile'] || '/user-profile';
        this.router.navigateByUrl(returnUrl);
    },
      error => {
        this.toastrService.error("Cập nhập thông tin user thất bại");
      })

  }

  callUserDetail() {
    this.accountService.getUser().subscribe((res) => {
        const data = res?.data;
        //set data
        this.form.controls['email'].setValue(data?.email);
        this.form.controls['full_name'].setValue(data?.full_name);
        this.form.controls['phone_number'].setValue(data?.phone_number);
        this.form.controls['age'].setValue(data?.age);
        this.form.controls['date_of_birth'].setValue(data?.date_of_birth);
        this.form.controls['province'].setValue(data?.address?.province);
        this.form.controls['district'].setValue(data?.address?.district);
        this.form.controls['city'].setValue(data?.address?.city);
        this.form.controls['ward'].setValue(data?.address?.ward);
        this.form.controls['extra'].setValue(data?.address?.extra);
        this.urlImg = (data?.avatar != null?data?.avatar :'');
      },
      error => {
        this.toastrService.error("lỗi lấy thông tin user");
        console.log(error);
      });

  }
  moveToUploadFile(){
    // this.router.navigate(['upload-avatar']);
    this.onOpenModal("upload");
  }
  public onOpenModal(mode: string): void{
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'upload') {
      // this.deleteProduct = Product;
      button.setAttribute('data-target', '#upLoadModal');
    }
    container.appendChild(button);
    button.click();
  }
  selectFile(event: any): void {
    this.message = '';
    this.preview = '';
    this.progress = 0;
    this.selectedFiles = event.target.files;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.preview = '';
        this.currentFile = file;

        const reader = new FileReader();

        reader.onload = (e: any) => {
          console.log(e.target.result);
          this.preview = e.target.result;
        };

        reader.readAsDataURL(this.currentFile);
      }
    }
  }
  upload(): void {
    this.progress = 0;
    let flagMessage = true;
    let count = 0;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;
        this.uploadService.upload(this.currentFile).subscribe({
          next: (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress = Math.round((100 * event.loaded) / event.total);
            } else if (event instanceof HttpResponse) {
              this.message = event.body.message;
              this.imageInfos = this.uploadService.getFiles();
            }
            this.callUserDetail();
            if(flagMessage) {
              this.toastrService.success("Upload avatar thành công")
              flagMessage = false;
            }
            // const returnUrl = this.route.snapshot.queryParams['/user-profile'] || '/user-profile';
            // this.router.navigateByUrl(returnUrl);
            // window.location.reload()
            // window.location.href=`${environment.pageApi}/user-profile`
          },
          error: (err: any) => {
            console.log(err);
            this.progress = 0;

            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Upload file thất bại!';
            }
            this.toastrService.error(this.message);

            this.currentFile = undefined;
          },
          complete(){
            this.toastrService.success("Upload avatar thành công");
            this.closeButton.nativeElement.click();
          }
        });

      }

      this.selectedFiles = undefined;
    }
  }
  // async sleep(ms: number): Promise<void> {
  //   return new Promise(
  //     (resolve)
  //       => setTimeout(resolve, ms));
  // }

  logOut() {
    this.accountService.logout();
  }
}

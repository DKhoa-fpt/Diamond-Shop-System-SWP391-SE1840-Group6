import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from "../../../modules/service/file-upload.service";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css'],
})
export class FileUploadComponent implements OnInit{

  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';
  preview = '';

  imageInfos?: Observable<any>;

  constructor(private uploadService: FileUploadService,
              private toastrService: ToastrService,
              private route: ActivatedRoute,
              private router: Router,) {}

  ngOnInit(): void {
    // this.imageInfos = this.uploadService.getFiles();
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
    var flagMessage = true;

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
            if(flagMessage) {
              this.toastrService.success("Upload avatar thành công")
              flagMessage = false;
            }
            // const returnUrl = this.route.snapshot.queryParams['/user-profile'] || '/user-profile';
            // this.router.navigateByUrl(returnUrl);
            window.location.reload()
            window.location.href=`${environment.pageApi}/user-profile`
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
            this.closebutton.nativeElement.click();
        }
        });
      }

      this.selectedFiles = undefined;
    }
  }
}

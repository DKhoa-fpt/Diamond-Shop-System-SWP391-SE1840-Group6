import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { ProductDTO } from "../../../modules/_models/productDTO";
import { HelperService } from "../../../modules/service/helper.service";
import { ProductService } from "../../../modules/service/product.service";
import { StorageService } from "../../../modules/service/storage.service";
import { Router } from '@angular/router';
import {ToastrService} from "ngx-toastr";
class ImageSnippet {
  pending: boolean = false;
  status: string = 'init';

  constructor(public src: string, public file: File) {}
}
@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent {
  public Products: any[] = [];
  public editProduct!: any;
  public deleteProduct!: any;
  selectedFile: ImageSnippet;
  public jewelryTypes : any[] = [];

  constructor(private helperService: HelperService,
              private ProductService: ProductService,
              private storageService: StorageService,
              private router: Router,
              private toastrService: ToastrService) {
  }

  ngOnInit(): void {
    this.getProducts();
    this.getJewelryTypes();
    throw new Error('Method not implemented.');
  }
  public getProducts(): void{
    let request = {
      jewelry_type_id:'',
      limit:1000,
      offset:0,
      requestId:''
    }
    this.ProductService.getProducts(request).subscribe(
      (response:any) =>{
        this.Products = response.data;
      },
      (error: HttpErrorResponse)=>{alert(error.message);}
    );
  }
  public onAddProduct(addForm: NgForm): void {
    const formData: FormData = new FormData();
    if(this.selectedFile) {
      formData.append('image',this.selectedFile.file);
      formData.append('request',new Blob([JSON.stringify(addForm.value)], {
        type: 'application/json',
      }));
      document.getElementById('add-Product-form')!.click();
      this.ProductService.addProduct(formData).subscribe(
        (response: any) => {
          console.log(response);
          this.toastrService.success("Add product success")
          this.getProducts();
          addForm.reset();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
          this.selectedFile.file = null;
        }
      );
    } else {
      this.toastrService.error(" Không được để trống ảnh");
    }
  }
  public logoutPage():void{
    this.storageService.clean();
    this.router.navigate(['/login']);

  }

  public onUpdateProduct(product: any): void {
    const formData: FormData = new FormData();
    console.log(this.selectedFile);
    if(this.selectedFile != undefined) {
      formData.append('image',this.selectedFile?.file!=undefined ?this.selectedFile.file:null);
    }
      formData.append('request',new Blob([JSON.stringify(product)], {
        type: 'application/json',
      }));
      this.ProductService.updateProduct(formData).subscribe(
        (response: any) => {
          this.toastrService.success("Update product success")
          this.getProducts();
          this.selectedFile.file = null;
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  public onDeleteProduct(ProductId: any): void {
    if(ProductId !== undefined){
      ProductId = Number(ProductId);
      this.ProductService.deleteProduct(ProductId).subscribe(
        (response: void) => {
          console.log(response);
          this.getProducts();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );

    }else{
      console.log("error type ID");
    }
  }

  public searchProducts(key: string): void {
    console.log(key);
    const results: ProductDTO[] = [];
    for (const Product of this.Products) {
      if (Product.name?.toLowerCase().indexOf(key.toLowerCase()) !== -1
        // || Product.mail?.toLowerCase().indexOf(key.toLowerCase()) !== -1
        // || Product.phone?.toLowerCase().indexOf(key.toLowerCase()) !== -1
        // || Product.jobTitle?.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ) {
        results.push(Product);
      }
    }
    this.Products = results;
    if (results.length === 0 || !key) {
      this.getProducts();
    }
  }

  public onOpenModal(Product: any, mode: string): void{
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addProductModal');
    }
    if (mode === 'edit') {
      this.editProduct = Product;
      button.setAttribute('data-target', '#updateProductModal');
    }
    if (mode === 'delete') {
      this.deleteProduct = Product;
      button.setAttribute('data-target', '#deleteProductModal');
    }
    container.appendChild(button);
    button.click();
  }

  processFile(imageInput: any) {
    console.log(imageInput.files[0]);
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);

      // this.imageService.uploadImage(this.selectedFile.file).subscribe(
      //   (res) => {
      //
      //   },
      //   (err) => {
      //
      //   })
    });

    reader.readAsDataURL(file);
  }

  private getJewelryTypes() {
    this.ProductService.getJewelryType().subscribe((res) =>{
      this.jewelryTypes = [...res.data];
      console.log(this.jewelryTypes);
    },error => {
      this.toastrService.error("Error get Type")
    })

  }
}

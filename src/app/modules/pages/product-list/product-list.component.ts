import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../../service/product.service";
import {ToastrService} from "ngx-toastr";
import {NumberService} from "../../service/number.service";
import {AccountService} from "../../auth/services/account.service";
import {AuthGoogleService} from "../../../core/shared/auth-google.service";
import {CartService} from "../../service/cart.service";
import {FormBuilder, FormGroup, FormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit{
  selectTypePrice :string = '';
  selectOptionType :string = '';
  searchProduct: string = '';
  products: any[];
  isLoginUser:boolean = false;
  formSearch!:FormGroup;
  selectedTypePrice: string='Nothing';
  page = 1;
  count = 0;
  pageSize = 9;
  pageSizes = [3, 6, 9];
  total = 0;
  selectPrices = [
    {
      name:"Price 0->50 millions VND",
      id:1
    },
    {
      name:"Price 50->100 millions VND",
      id:2
    },
    {
      name:"Price upper 100 millions VND",
      id:3
    },
  ];
  public jewelryTypes : any[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private productService: ProductService,
              private toastrService: ToastrService,
              private numberFormat: NumberService,
              private accountServie: AccountService,
              private googleService :AuthGoogleService,
              private cartService: CartService,
              private ProductService: ProductService,
              private formBuilder: FormBuilder,) {
  }
  ngOnInit(): void {
    this.formSearch = this.formBuilder.group({typePrice: ['']});
    this.isLoginUser = localStorage.getItem("user") != null;
    this.getJewelryTypes();
    this.getProducts();
  }

  onChange(event: Event) {
    const selectedValue = (event.target as HTMLSelectElement).value;
    console.log(selectedValue);
  }

  removeFilterPrice() {
    // this.selectTypePrice = "";
    // console.log(this.selectTypePrice);
  }
  handlePageChange(event: number): void {
    this.page = event;
    this.getProducts();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.getProducts();
  }
  getProducts(){
    let request = {
      jewelry_type_id:this.selectOptionType,
      title:this.searchProduct,
      limit:this.pageSize,
      offset:(this.page-1)*this.pageSize,
      requestId:''
    } as any;
    if(this.selectedTypePrice =="budget1"){
      request.budget1 = true;
    }else if (this.selectedTypePrice =="budget2"){
      request.budget2 = true;
    }else if(this.selectedTypePrice =="budget3"){
      request.budget3 = true;
    }

    this.productService.getProducts(request).subscribe((res) => {
      this.products = res.data;
      this.count = res?.meta?.total;
    }, error => {
      this.toastrService.error("Error get famous products");
    });
  }
  convertNumber(number){
    return this.numberFormat.convertNumber(number);
  }

  search() {
    this.page = 1;
    this.getProducts();
  }
  private getJewelryTypes() {
    this.ProductService.getJewelryType().subscribe((res) =>{
      this.jewelryTypes = [...res.data];
      console.log(this.jewelryTypes);
      console.log(this.selectPrices);
    },error => {
      this.toastrService.error("Error get Type")
    })

  }
  getProductCart() {
    const request = {
      customer_id : 1
    }
    this.cartService.getProductInCart(request).subscribe((res) =>{
      this.cartService.cartItems.next(res?.data);
      this.cartService.totalProductInCart.next(this.cartService.getTotalProduct(res?.data));
      this.cartService.totalPrice.next(this.cartService.getTotalPriceV2(res?.data));
    }, error => {

    })
  }
  addProductCart(product:any){
    if(!this.isLoginUser) {
      this.toastrService.error("Bạn phải đăng nhập trước");
      return;
    }
    const request = {
      jewelry_id : product?.id_jewelry,
      quantity : 1
    }
    this.cartService.addProductToCard(request).subscribe((res) =>{
      this.toastrService.success("Add sản phầm thành công");
      this.getProductCart();
    }, error => {
      this.toastrService.error("Add sản phầm thất bại");
    });
  }
}

import {Component, Input} from '@angular/core';
import {NumberService} from "../../service/number.service";
import {ProductService} from "../../service/product.service";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  @Input() productId = 1;
  isLoginUser:boolean = false;
  product:any;
  totalProduct =1;
  listImgUrl = ['assets/images/ring-slider2.png','assets/images/ring-slider2.png','assets/images/ring-slider2.png','assets/images/ring-slider2.png']
  listDiamond : any[];
  selectSizeDiamond: string;
  constructor(private numberFormat: NumberService,
              private productService: ProductService,
              private toastrService: ToastrService,
              private activatedRoute: ActivatedRoute,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.isLoginUser = localStorage.getItem("user") != null;
    // this.product = {
    //   "id_jewelry": 3,
    //   "jewelry_title": "Nhẫn nữ đính kim cương sang trọng 18K",
    //   "jewelry_code": "NNKC03",
    //   "jewelry_type": "Nhẫn Kim Cương Nữ",
    //   "jewelry_type_id": 2,
    //   "quantity": 100,
    //   "price": 1234000.0,
    //   "created_at": null,
    //   "created_by": "Tran Khoa",
    //   "updated_at": null,
    //   "updated_by": "",
    //   "description": "Nhẫn kim cương nữ vàng trắng 18K CH 0163 cũng là một thiết kế đột phá của thương hiệu Cao Hùng. Dù không sử dụng viên kim cương chủ có kích thước lớn nhưng chiếc nhẫn vẫn tỏa sáng theo một cách rất riêng.",
    //   "image_id": null,
    //   "type_enum": null,
    //   "diamond_id": "36000000-0000-0000-0000-000000000000"
    // };
    this.getDiamondList();
    this.activatedRoute.queryParams.subscribe(params => {
      let id = params['id'];
      this.productService.getProductDetail(id!=null ?id:this.productId).subscribe((data) => {
          this.product = data?.data;
        },
        (error) => {
          console.log(error)
        });
    });
  }
  convertNumber(number){
    return this.numberFormat.convertNumber(number);
  }
  changeTotalProduct(number:number) {
    if(this.totalProduct == 1 && number <0){
      this.toastrService.error("số lượng không thể nhỏ hơn 1");

    } else {
      this.totalProduct +=number
    }
  }
  getProductCart() {
    const request = {
      customer_id : null
    }
    this.cartService.getProductInCart(request).subscribe((res) =>{
      this.cartService.cartItems.next(res?.data);
      this.cartService.totalProductInCart.next(this.cartService.getTotalProduct(res?.data));
      this.cartService.totalPrice.next(this.cartService.getTotalPriceV2(res?.data));
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
      this.cartService.totalPrice.next(this.cartService.getTotalPriceV2(res?.data));
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
      quantity : 1,
    } as any;
    if (this.selectSizeDiamond && this.selectSizeDiamond !== '') {
      request.size = this.selectSizeDiamond;
    }
    this.cartService.addProductToCard(request).subscribe((res) =>{
      this.toastrService.success("Add product to cart success");
      this.getProductCart();
    }, error => {
      this.toastrService.error("Add product to cart fail");
    });
  }
  getDiamondList() {
    this.productService.getDiamondList().subscribe((res) =>{
      this.listDiamond = res;
    }, error => {
      this.toastrService.error("Get diamond list fail!!!");
    })
  }

  onChange(event: Event) {
    const selectedValue = (event.target as HTMLSelectElement).value;
    this.selectSizeDiamond = selectedValue;
    const diamond = this.listDiamond.find(({name}) => name === selectedValue);
    if(diamond) {
      this.product.price = this.product.price + diamond.price;
    }
  }
}

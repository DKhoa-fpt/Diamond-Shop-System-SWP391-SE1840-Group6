import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgOtpInputModule } from  'ng-otp-input';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DefaultLayoutModule } from './core/default-layout/default-layout.module';
import { DashboardModule } from './core/check/dashboard/dashboard.module';
import { LoginComponent } from './modules/auth/login/login.component';
import { SharedAppModule } from './core/shared/shared.module';
import { RegisterComponent } from './modules/auth/login/register/register.component';
// import { ResetPasswordComponent } from './modules/auth/reset-password/reset-password.component';
// import { FeatureGuard } from './core/permission/guards/feature.guard';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
// import { AuthInterceptor } from './core/shared/interceptors/auth.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './core/check/home/home.component';
import { OAuthModule } from 'angular-oauth2-oidc';
import { LoginV3Component } from './core/check/login-v3/login-v3.component';
import { IntroductionComponent } from './modules/default-page/introduction/introduction.component';
import {ToastrModule} from "ngx-toastr";
import { PriceDimondTableComponent } from './modules/default-page/price-dimond-table/price-dimond-table.component';
import { MeasureJewelrySizeComponent } from './modules/default-page/measure-jewelry-size/measure-jewelry-size.component';
import { AddProductComponent } from './core/check/add-product/add-product.component';
// import { CartComponent } from './modules/cart/cart.component';
import { UserProfileComponent } from './core/check/user-profile/user-profile.component';
import {CommonModule} from "@angular/common";
import { HeaderComponent } from './core/check/header/header.component';
import { FileUploadComponent } from './core/check/file-upload/file-upload.component';
import { HomePageComponent } from './modules/pages/home-page/home-page.component';
import { AboutComponent } from './modules/pages/about/about.component';
import { ArticleComponent } from './modules/pages/article/article.component';
import { BlogComponent } from './modules/pages/blog/blog.component';
import { CheckoutComponent } from './modules/pages/checkout/checkout.component';
import { ContactUsComponent } from './modules/pages/contact-us/contact-us.component';
import { FaqsComponent } from './modules/pages/faqs/faqs.component';
import { MyLoginComponent } from './modules/auth/my-login/my-login.component';
import { MyAccountComponent } from './modules/auth/my-account/my-account.component';
import { OrderSummeryComponent } from './modules/pages/order-summery/order-summery.component';
import { PrivacyPolicyComponent } from './modules/pages/privacy-policy/privacy-policy.component';
import { ProductListComponent } from './modules/pages/product-list/product-list.component';
import { ProductComponent } from './modules/pages/product/product.component';
import { RegisterPageComponent } from './modules/auth/register-page/register-page.component';
import { WishListComponent } from './modules/pages/wish-list/wish-list.component';
import { MyHeaderComponent } from './modules/layout/my-header/my-header.component';
import { MyFooterComponent } from './modules/layout/my-footer/my-footer.component';
import { MyMiniCartComponent } from './modules/layout/my-mini-cart/my-mini-cart.component';
import {ResetPasswordComponent} from "./modules/auth/reset-password/reset-password.component";
import { ForgetPasswordComponent } from './modules/auth/forget-password/forget-password.component';
import {CartComponent} from "./modules/pages/cart/cart.component";
import {FormsModule} from "@angular/forms";
import { OrderInfoComponent } from './modules/pages/order-info/order-info.component';
import { OrderListComponent } from './modules/pages/order-list/order-list.component';
import { BillResultComponent } from './modules/pages/bill-result/bill-result.component';
import {LoginSuccessComponent} from "./modules/auth/login-success/login-success.component";
import {NgxPaginationModule} from "ngx-pagination";
import { HuongDanDoSizeNhanComponent } from './modules/default-page/huong-dan-do-size-nhan/huong-dan-do-size-nhan.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ResetPasswordComponent,
    HomeComponent,
    LoginV3Component,
    IntroductionComponent,
    PriceDimondTableComponent,
    MeasureJewelrySizeComponent,
    AddProductComponent,
    CartComponent,
    UserProfileComponent,
    HeaderComponent,
    FileUploadComponent,
    HomePageComponent,
    AboutComponent,
    ArticleComponent,
    BlogComponent,
    CheckoutComponent,
    ContactUsComponent,
    FaqsComponent,
    MyLoginComponent,
    MyAccountComponent,
    OrderSummeryComponent,
    PrivacyPolicyComponent,
    ProductListComponent,
    ProductComponent,
    RegisterPageComponent,
    WishListComponent,
    MyHeaderComponent,
    MyFooterComponent,
    MyMiniCartComponent,
    ForgetPasswordComponent,
    OrderInfoComponent,
    OrderListComponent,
    BillResultComponent,
    LoginSuccessComponent,
    HuongDanDoSizeNhanComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    DefaultLayoutModule,
    DashboardModule,
    SharedAppModule,
    BrowserAnimationsModule,
    NgbModule,
    CommonModule,
    FormsModule,
    OAuthModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 150000, // 15 seconds
      closeButton: true,
      progressBar: true,
    }),
    NgOtpInputModule,
    NgxPaginationModule


  ],
  providers: [
    // FeatureGuard,
    // {
    //   provide: LocationStrategy,
    //   useClass: HashLocationStrategy
    // },
    // {
    //   provide: HTTP_INTERCEPTORS,
    //   useClass: AuthInterceptor,
    //   multi: true,
    // },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

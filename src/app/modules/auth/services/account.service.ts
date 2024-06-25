import { Injectable } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from "../../../../environments/environment";

import { User } from "../../_models";
import {AuthGoogleService} from "../../../core/shared/auth-google.service";

@Injectable({ providedIn: 'root' })
export class AccountService {
    userSubject: BehaviorSubject<any | null>;
    public user: Observable<any | null>;
    httpOptions: any;

    public isLoginWithGoogle = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        private authGoogleService: AuthGoogleService,
    ) {
        this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
        this.user = this.userSubject.asObservable();
        this.httpOptions = {
            headers: new HttpHeaders({
                "Content-Type": "application/json",
              "Authorization": `Bearer ${JSON.parse(localStorage.getItem('user')!)?.accessToken}`,
            }),
            "Access-Control-Allow-Origin": `${environment.apiUrl}`,
            "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",

        };
    }

    public get userValue() {
        return this.userSubject.value;
    }
    public  changeLoginWithGoogle(status:boolean){
      this.isLoginWithGoogle = status;
    }

    login(username: string, password: string) {

      const credentials = {
        email: username,
        password: password
      };

      const jsonString = JSON.stringify(credentials);
      // let kq = '{\'email\':'+username+',\'password\':' +password+'}';
      //   const body = JSON.parse(kq);
      //   console.log(body);
          // new HttpParams()
          //   .set(`email`, username)
          //   .set(`password`, password);
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
        "Access-Control-Allow-Origin": `*`,
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
      };
      return this.http.post<any>(`${environment.apiUrl}/shop/auth/login`,jsonString,httpOptions)
            .pipe(map(user => {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(user));
                this.userSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.authGoogleService.logout();
        this.router.navigate(['/my-login'], { relativeTo: this.route });
    }

    register(user: any) {
        return this.http.post(`${environment.apiUrl}/shop/auth/signup`, user,this.httpOptions);
    }
  loginWithGoogle(user: any):Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/social/google`, user,this.httpOptions);
  }

    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }

    getById(id: string) {
        return this.http.get<User>(`${environment.apiUrl}/users/${id}`);
    }
    getByUsername(username: string) {
        return this.http.get<User>(`${environment.apiUrl}/api/user/get?username=${username}`);
    }

    update(id: string, params: any) {
        return this.http.put(`${environment.apiUrl}/users/${id}`, params)
            .pipe(map(x => {
                // update stored user if the logged in user updated their own record
                if (id == this.userValue?.id) {
                    // update local storage
                    const user = { ...this.userValue, ...params };
                    localStorage.setItem('user', JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }
    updateUser(user:any) :Observable<any> {
        return this.http.post<any>(`${environment.apiUrl}/shop/user/change-profile`,user,this.httpOptions);
    }
    getUser():Observable<any> {
      return  this.http.get<any>(`${environment.apiUrl}/shop/user`,this.httpOptions);
    }
    getOtp(email:string):Observable<any> {
      return  this.http.get<any>(`${environment.apiUrl}/shop/otp/register?email=${email}`,this.httpOptions);
    }
  getOtpFogetPassWord(email:string):Observable<any> {
    return  this.http.get<any>(`${environment.apiUrl}/shop/otp/forget?email=${email}`,this.httpOptions);
  }
  changePassWord(request:any):Observable<any> {
      return this.http.put<any>(`${environment.apiUrl}/shop/user/change-pass`,request,this.httpOptions);
}
    getAllUser(): Observable<any> {
      return this.http.get<any>(`${environment.apiUrl}/shop/admin/account`, this.httpOptions);
    }

    delete(id: string) {
        return this.http.delete(`${environment.apiUrl}/users/${id}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (id == this.userValue?.id) {
                    this.logout();
                }
                return x;
            }));
    }
}

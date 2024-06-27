import { API_ENDPOINTS, ApiMethod } from '../../shared/utils/const';
import { ApiHandlerService } from '../../shared/utils/api-handler.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: ApiHandlerService) { }

  getUsers() {
    return this.http.requestCall(API_ENDPOINTS.users,ApiMethod.GET,'')
  }

  deleteUser(i:any) {
    return this.http.requestCall(API_ENDPOINTS.deleteUser,ApiMethod.GET,i)
  }

  editUser(data:any) {
    return this.http.requestCall(API_ENDPOINTS.editUser,ApiMethod.POST,'',data)
  }

  createUser(data:any) {
    return this.http.requestCall(API_ENDPOINTS.createUser,ApiMethod.POST,'',data)
  }
}

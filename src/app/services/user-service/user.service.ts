import { Injectable } from '@angular/core';
import {HttpBaseService} from "../http-base-service/http-base.service";
import {environment} from "../../../environments/environment.development";
import {Observable} from "rxjs";
import {APIResponseModel} from "../../model/interface";
import {User} from "../../model/class";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpBaseService: HttpBaseService) { }

  getAllUsersWithFullInfo(): Observable<APIResponseModel> {
    return this.httpBaseService.get(environment.httpBaseUrl + 'user/all');
  }

  createUser(user: User): Observable<APIResponseModel> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'user', user);
  }

  deleteUser(userId: number): Observable<any> {
    return this.httpBaseService.delete(environment.httpBaseUrl + 'user/' + userId);
  }

  updateUser(user: User): Observable<APIResponseModel> {
    return this.httpBaseService.put(environment.httpBaseUrl + 'user', user);
  }

  getAllUserStatus(): Observable<APIResponseModel> {
    return this.httpBaseService.get(environment.httpBaseUrl + 'user/status');
  }
}

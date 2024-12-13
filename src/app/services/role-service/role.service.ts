import { Injectable } from '@angular/core';
import {HttpBaseService} from "../http-base-service/http-base.service";
import {environment} from "../../../environments/environment.development";
import {APIResponseModel} from "../../model/interface";
import {Observable} from "rxjs";
import {Role} from "../../model/class";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private httpBaseService: HttpBaseService) { }

  getAllRolesFullInfo(): Observable<APIResponseModel> {
    return this.httpBaseService.get(environment.httpBaseUrl + 'role/all');
  }

  getAllRolesBasicInfo(): Observable<APIResponseModel>{
    return this.httpBaseService.get(environment.httpBaseUrl + 'role/all-basic');
  }

  deleteRole(roleName: string): Observable<APIResponseModel> {
    return this.httpBaseService.delete(environment.httpBaseUrl + 'role/' + roleName);
  }

  updateRole(role: Role): Observable<APIResponseModel> {
    return this.httpBaseService.put(environment.httpBaseUrl + 'role', role);
  }
}

import { Injectable } from '@angular/core';
import {HttpBaseService} from "../http-base-service/http-base.service";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment.development";
import {jwtDecode} from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpBaseService: HttpBaseService) { }

  getDecodedToken(token: string): any {
    try {
      return jwtDecode(token);
    }catch (error){
      console.error('Invalid token: ', error);
      return null;
    }
  }

  getRoles():string[]{
    const token = localStorage.getItem('authToken');
    if(!token)
      return [];

    console.log(token);

    const decodedToken = this.getDecodedToken(token);
    return decodedToken.scope.split(' ') as string[] ;
  }

  hasRole(role: string): boolean {
    const roles = this.getRoles();
    return roles.includes(role);
  }

  login(username: string, password: string): Observable<any> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'auth/log-in', {username, password});
  }

  refreshToken(body: { token: string }): Observable<any> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'auth/refresh', body);
  }

  logout(token: any): Observable<any> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'auth/logout', token);
  }
}

import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth-service/auth.service";
import {Router} from "@angular/router";
import {catchError, switchMap, throwError} from "rxjs";
import {APIResponseModel} from "../model/interface";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('authToken');

  const PUBLIC_ENDPOINTS = ["/auth/log-in", "/auth/introspect", "/auth/logout", "/auth/refresh"];

  const isPublicEndpoint = PUBLIC_ENDPOINTS.some(ep => (req.url.includes(ep) && req.method === 'POST') || req.url.includes('/article/search'));

  const authService = inject(AuthService);
  const router = inject(Router);

  if (isPublicEndpoint) {
    return next(req);
  }else if (token) {
    //Clone request và thêm header nếu token tồn tại
    const cloneRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    //Gửi request kèm header đã thêm token
    return next(cloneRequest).pipe(
      catchError(err => {
        if(err.status === 401){
          //Nếu gặp lỗi 401 thì thử refresh token
          const body ={
            token: token
          }

          return authService.refreshToken(body).pipe(
            switchMap((res: APIResponseModel) => {
              if(res.result.authenticated) {
                //Nếu refresh thành công thì ghi token mới với localStorage
                localStorage.setItem('authToken', res.result.token);
              }

              //Clone request với token mới
              //req.headers.set('Authorization', `Bearer ${res.result.token}`);

              const newRequest = req.clone({
                setHeaders: {
                  Authorization: `Bearer ${localStorage.getItem('authToken')}`,
               }
              });

              //Gửi lại request ban đầu với token mới
              return next(newRequest);
            }),
            catchError(err => {
              if(err.status === 401){
                //Nếu yêu cầu refresh thất bại thì chuyển về trang login
                router.navigateByUrl('login')
              }
              return throwError(() => err);
            })
          );
        }
        return throwError(() => err);
      }),
    );
  }

  //Gửi request gốc không kèm token
  return next(req);
};

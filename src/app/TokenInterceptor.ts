import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const userToken = localStorage.getItem('token');
    let modifiedReq;
    if (!req.url.endsWith('authenticate')){
         modifiedReq = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${userToken}`),
          });

    } else {
        modifiedReq = req;
    }
    return next.handle(modifiedReq);
  }
}

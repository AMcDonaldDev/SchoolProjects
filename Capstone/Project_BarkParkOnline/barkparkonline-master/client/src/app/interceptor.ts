import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class Interceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      console.log("Intercepting HTTP Request");
      // add authorization header with basic auth credentials if available
      let currentUser = JSON.parse(localStorage.getItem('currentUser'));
      console.log("--> Using local storage's currentUser:");
      console.log(currentUser);

      try {
        let credentials = currentUser.credentials;
        let headers = new HttpHeaders();
        if (credentials != null) {
          headers = new HttpHeaders(credentials ? {
            authorization: 'Basic ' + btoa(credentials.username + ":" + credentials.password)
          } : {});
          request = request.clone( {
            headers: headers
          });
        }
      } catch (error) {
        // Current User was null
      }
      request.headers.append('X-Requested-With', 'XMLHttpRequest')
      return next.handle(request);
    }
}

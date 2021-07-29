import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from 'src/environments/environment';
import { Resolve } from '@angular/router';
import { AppService } from '../app-service';

@Injectable()
export class LogoutResolver implements Resolve<any> {
  constructor(private http: HttpClient, private appService: AppService) {}

  resolve() {
    this.appService.loggedIn = false;
    return this.http.get(env.hostUrl + "user/logout");
  }
}

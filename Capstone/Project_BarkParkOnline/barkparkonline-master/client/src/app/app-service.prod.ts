import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from '../environments/environment';
import { UserProfile } from './sign-up/classes/user-profile';
import { BasicUser } from './sign-up/classes/basic-user';
import { Router } from '@angular/router';
import { Credentials } from './sign-up/classes/credentials';
import { DogProfile } from './sign-up/classes/dog-profile';
import { ApiResponse } from './api-response';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppService {

  private user: BasicUser;

  public login$ = new BehaviorSubject<BasicUser>(new BasicUser());

  public loggedIn: boolean = false;
  public loginFailed: boolean = false;
  public username: string = "";

  constructor(private http: HttpClient, private router: Router) {
    this.user = new BasicUser();
    this.login(null);
  }

  public login(credentials: Credentials) {
    let headers = new HttpHeaders();
    this.user.credentials = new Credentials();
    if (credentials != null) {
      headers = new HttpHeaders(credentials ? {
        authorization: 'Basic ' + btoa(credentials.username + ":" + credentials.password)
      } : {});
      this.user.credentials = credentials;
    }

    this.http.get<any>(env.hostUrl + "user/login", { headers: headers }).subscribe(
      response => {
        this.user.userProfile = new UserProfile().fromJSON(response.userProfile);
        this.user.credentials.email = response.credentials.email;
        this.loggedIn = true;

        if (credentials != null) {
          sessionStorage.setItem(
            'token',
            btoa(credentials.username + ':' + credentials.password)
          );
        }

        let currentNav = (<any>this.router).location._platformLocation.location.toString();
        if (currentNav.includes('sign-up') || currentNav.includes('login')) {
          this.router.navigateByUrl("/");
        }

        let authenticatedUser = new BasicUser().fromJSON(response);
        this.login$.next(authenticatedUser);
      },
      error => {
        this.loggedIn = false;
        if (credentials != null) this.loginFailed = true;
      }
    )
  }

  public signUp(user: BasicUser) {

    this.http.post(env.hostUrl + "user/signup", user).subscribe(
      response => {
        this.login(user.credentials);
      },
      error => {
        console.error(error);
      }
    )
  }

  public fetchParks(offset: number, limit: number, sort: string)
  {
      return this.http.get<any>(env.hostUrl+ "park/all/?offset="+offset+"&limit="+limit+"&sortField="+sort);
  }

  public fetchPark(parkId: string) {
    return this.http.get<ApiResponse>(env.hostUrl + "park/" + parkId);
  }

  public showLogin() { }

  public toggleLogin() { }

  public addDog(dog: DogProfile) {
    return this.http.post<ApiResponse>(env.hostUrl + "user/dog/add", dog);
  }

  public deleteDog(dog: string) {
    return this.http.delete<ApiResponse>(env.hostUrl + "user/dog/delete/" + dog);
  }

  public updateDog(dog: DogProfile) {
    return this.http.put<ApiResponse>(env.hostUrl + "user/dog/update", dog);
  }
}

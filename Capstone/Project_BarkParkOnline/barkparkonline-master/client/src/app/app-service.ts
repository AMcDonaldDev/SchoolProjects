import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { environment as env } from '../environments/environment';
import { UserProfile } from './sign-up/classes/user-profile';
import { BasicUser } from './sign-up/classes/basic-user';
import { Router } from '@angular/router';
import { Credentials } from './sign-up/classes/credentials';
import { DogProfile } from './sign-up/classes/dog-profile';
import { ApiResponse } from './api-response';
import { BehaviorSubject } from 'rxjs';

export interface VisitResponse {
  accepted: boolean;
  message: any;
}

@Injectable({
  providedIn: 'root',
})
export class AppService implements OnInit {

  private user: BasicUser;

  public login$ = new BehaviorSubject<BasicUser>(new BasicUser());

  public loggedIn: boolean = true;
  public loginFailed: boolean = false;
  public username: string = "";

  constructor(private http: HttpClient, private router: Router) {
    this.user = new BasicUser();
    this.ngOnInit();
  }

  ngOnInit(): void {
    let cachedUser = localStorage.getItem("currentUser");
    if (cachedUser != null) {
      console.log("Getting User from Local Storage");
      this.user = JSON.parse(cachedUser);
      console.log(this.user);
      this.login(this.user.credentials);
    }
    else { // See if user is already logged in
      this.login(null);
    }

    this.login$.subscribe(
      (user) => {
        console.log("Logged in succesfully!");
        this.loggedIn = true;
      },
      (error) => {
        console.log("Login failed!");
        this.loggedIn = false;
      }
    );
  }

  public login(credentials: Credentials) {
    let headers = new HttpHeaders();
    if (credentials != null) {
      headers = new HttpHeaders(credentials ? {
        authorization: 'Basic ' + btoa(credentials.username + ":" + credentials.password)
      } : {});
    }

    this.user.credentials = credentials;
    this.http.get<any>(env.hostUrl + "user/login", { headers: headers }).subscribe(
      response => {
        this.user.userProfile = new UserProfile().fromJSON(response.userProfile);
        this.user.credentials.email = response.credentials.email;
        console.log("Saving User to Local Storage");
        localStorage.setItem('currentUser', JSON.stringify(this.user));
        sessionStorage.setItem(
          'token',
          btoa(credentials.username + ':' + credentials.password)
        );
        let currentNav = (<any>this.router).location._platformLocation.location.toString();
        if (currentNav.includes('sign-up') || currentNav.includes('login')) {
          this.router.navigateByUrl("/");
        }

        console.log("Authentication Successful!");
        let authenticatedUser = new BasicUser().fromJSON(response);
        this.loggedIn = true;
        this.login$.next(authenticatedUser);
      },
      error => {
        this.login$.error(error);
        if (credentials != null) this.loginFailed = true;
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

  public signUp(user: BasicUser) {
    this.http.post(env.hostUrl + "user/signup", user).subscribe(
      response => {
        console.log("Signed up");
        this.login(user.credentials);
      },
      error => {
        console.log("Sign up failed")
        throw error;
      }
    )
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

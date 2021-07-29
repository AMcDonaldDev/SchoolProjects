import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppService } from '../app-service';
import { Credentials } from '../sign-up/classes/credentials';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public credentials: Credentials;

  public appService: AppService;

  constructor(private http: HttpClient, appService: AppService) {
    this.appService = appService;
    this.credentials = new Credentials();
  }

  ngOnInit(): void {

    /*
    "visitorFullName": "Ryan Austin",
    "visitorDogNames": ["Azula"],
    "date": "2020-11-27",
    "time": "12:01",
    "parkId": "5fc478e083d7c5476e02de7a"
    */



    let visit = {}
    visit["visitorFullName"] = "Ryan Austin";
    visit["visitorDogNames"] = ["Azula"];
    visit["date"] = "2020-11-27";
    visit["time"] = "12:01";
    visit["parkId"] = "5fc478e083d7c5476e02de7a";

  }

  public keyPressed(event: any) {
    if (event.keyCode == 13) {
      this.appService.login(this.credentials);
    }
  }
}

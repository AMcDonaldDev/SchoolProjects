import { Component, OnInit } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { AppService } from './app-service';
import { VisitScheduler } from './calendar/visit-scheduler/visit-scheduler.component';
import { BasicUser } from './sign-up/classes/basic-user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Bark Park Online';

  public appService: AppService;

  public user: BasicUser;

  constructor(
    appService: AppService,
    private _bottomSheet: MatBottomSheet
  ) {
    this.appService = appService;
    this.user = new BasicUser();
  }

  ngOnInit() {
    this.appService.login$.subscribe(user => this.user = user);
  }

  openVisitScheduler() {
    this._bottomSheet.open(VisitScheduler);
  }
}

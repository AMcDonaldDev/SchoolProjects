import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { AppService } from '../app-service';
import { environment as env } from '../../environments/environment';;
import { DogProfile } from '../sign-up/classes/dog-profile';
import { Visit } from '../calendar/visit';
import { BasicUser } from '../sign-up/classes/basic-user';
import { ThemePalette } from '@angular/material/core';
import { FormControl } from '@angular/forms';
import { CalendarService } from '../calendar/calendar.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-visit-schedule-form',
  templateUrl: './visit-schedule-form.component.html',
  styleUrls: ['./visit-schedule-form.component.css']
})
export class VisitScheduleFormComponent implements OnInit {

  //form selection update temp variables
  public currentDog = new DogProfile();
  public currentRemDog = new DogProfile();
  public currentVisit = new Visit();
  //accessing dog features
  public dogVisitList: Array<string>;
  public user: BasicUser;
  //Date picker stuff
  public date: moment.Moment;
  public disabled = false;
  public showSpinners = true;
  public showSeconds = false;
  public touchUi = false;
  public enableMeridian = false;
  public minDate: moment.Moment;
  public maxDate: moment.Moment;
  public stepHour = 1;
  public stepMinute = 1;
  public stepSecond = 1;
  public color: ThemePalette = 'primary';
  public dateControl = new FormControl(new Date());


  constructor(
    private activeModal: NgbActiveModal,
    private readonly http: HttpClient,
    private appService_: AppService,
    private calendarService_: CalendarService,
    private _snackBar: MatSnackBar
  ) {
    this.user = new BasicUser();
    this.dogVisitList = new Array<string>();
  }

  ngOnInit(): void {
    this.appService_.login$.subscribe(user => {
      this.user = user;
      for (let dog of this.user.userProfile.dogs) {
        this.dogVisitList.push(dog.dogName);
      }
    });
  }

  private handleError(error: Error) {
    this.displayMessage("Error: " + error.message);
  }

  public displayMessage(msg: string) {
    this._snackBar.open(msg, null, { duration: 2000 });
  }

  public close() {
    this.activeModal.dismiss();
  }

  public addVisit() {
    if (!this.dateControl.valid) {
      this.handleError(new Error("Date is not valid"));
      return;
    }
    if (this.dogVisitList.length == 0) {
      this.handleError(new Error("You must select a Dog for this Visit"));
      return;
    }

    let visit = new Visit();
    let date = new Date(this.dateControl.value);
    visit.date = date;
    visit.time = date.getHours() + ":" + date.getMinutes();
    visit.parkId = this.calendarService_.selectedPark._id;
    visit.visitorDogNames = this.dogVisitList;
    visit.visitorFullName = this.user.userProfile.fullName;
    visit = visit.finalize();
    this.calendarService_.addVisit(visit).subscribe(
      (response) => {

        if (response.statusCode >= 400) {
          this.handleError(new Error(response.message));
        } else {
          let body = response.message;
          for (let v of body.message) {
            this.user.userProfile.visitIds.push(v._id);
          }

          this.appService_.login$.next(this.user);

          this.displayMessage("Visit with " + visit.visitorDogNames.toString() +
            " on " + visit.date + " at " + visit.time + " scheduled!");
          this.calendarService_.refreshModel$.next(null);
          this.activeModal.dismiss();
        }
      },
      (error) => {
        this.handleError(error);
      }
    )
  }

  public getCurrentParkName(): string {
    return this.calendarService_.selectedPark.name;
  }
}

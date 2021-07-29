import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { ChangeDetectionStrategy } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { CalendarEvent } from 'angular-calendar';
import { Visit } from '../visit';
import { CalendarService } from '../calendar.service';
import { AppService } from 'src/app/app-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { BasicUser } from 'src/app/sign-up/classes/basic-user';
import { ThemePalette } from '@angular/material/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { BarkPark } from '../bark-park';

export interface VisitRow {
  visit: Visit;
  park: string;
  dogs: string;
  selectedDogs: string[];
  arriving: string;
  action: any;
  editing: boolean;
}

@Component({
  selector: 'visit-scheduler',
  templateUrl: './visit-scheduler.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./visit-scheduler.component.css']
})
export class VisitScheduler implements AfterViewInit {
  @ViewChild(MatTable) table: MatTable<any>;
  @ViewChild('picker') picker: any;

  public events: CalendarEvent[] = [];
  public refresh: Subject<any> = new Subject();
  public visit: Visit;

  public visitTable$: BehaviorSubject<Array<VisitRow>>;
  private visitTable: Array<VisitRow>;
  public displayedColumns: Array<string>;

  public user: BasicUser;

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

  public barkParkBuffer: Map<string, BarkPark>;

  public formGroup = new FormGroup({
    date: new FormControl(null, [Validators.required]),
    date2: new FormControl(null, [Validators.required])
  })
  public dateControl = new FormControl(new Date(2021, 9, 4, 5, 6, 7));
  public dateControlMinMax = new FormControl(new Date());

  public options = [
    { value: true, label: 'True' },
    { value: false, label: 'False' }
  ];

  public listColors = ['primary', 'accent', 'warn'];

  public stepHours = [1, 2, 3, 4, 5];
  public stepMinutes = [1, 5, 10, 15, 20, 25];
  public stepSeconds = [1, 5, 10, 15, 20, 25];

  constructor(
    private _bottomSheetRef: MatBottomSheetRef<VisitScheduler>,
    private _calendarService: CalendarService,
    private _appService: AppService,
    private _snackBar: MatSnackBar
  ) {
    this.visitTable = new Array<VisitRow>();
    this.visitTable$ = new BehaviorSubject(this.visitTable);
    this.displayedColumns = ['park', 'dogs', 'arriving', 'action'];
    this.user = new BasicUser();
    this.barkParkBuffer = new Map<string, BarkPark>();
  }

  ngAfterViewInit() {
    this._appService.login$.subscribe(
      (user) => {
        this.user = user;
        if (this.user.userProfile.visitIds.length > 0) {
          this.updateVisits(user);
        } else {
          this.displayMessage("You have no visits scheduled");
        }
      }
    )
  }

  private updateVisits(user) {
    this._calendarService.getManyVisits(user.userProfile.visitIds).subscribe(
      (response) => {
        if (response.statusCode >= 400) {
          this.handleError(new Error(response.message));
          return;
        }
        let visits = <Array<Visit>>response.message;
        let visitRows = new Array<VisitRow>();
        user.userProfile.visits = visits;
        for (let visit of visits) {
          visitRows.push(this.toVisitRow(visit));
        }
        // Get Real Names Async before rendering to table
        this.fetchParkNames(visitRows, this.barkParkBuffer);
      },
      (error) => {
        this.handleError(error);
      }
    );
  }

  openLink(event: MouseEvent): void {
    this._bottomSheetRef.dismiss();
    event.preventDefault();
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  private handleError(error: Error) {
    this.displayMessage("Error: " + error.message);
  }

  public displayMessage(msg: string) {
    this._snackBar.open(msg, null, { duration: 2000 });
  }

  public getVisit(visitRow: VisitRow): Visit {
    return visitRow.visit;
  }

  private updateVisitOnTable(visitRow: VisitRow, visit: Visit) {
    visitRow.visit = visit;
    visitRow.selectedDogs = visit.visitorDogNames;
    visitRow.dogs = visit.visitorDogNames.toString();
    visitRow.arriving = visit.date + " at " + visit.time;

    this.visitTable$.next(this.visitTable);
    this.table.renderRows();
  }

  private addVisitRowToTable(visitRow: VisitRow) {
    this.visitTable.push(visitRow);
    this.visitTable$.next(this.visitTable);
    this.table.renderRows();
  }

  private toVisitRow(visit: Visit): VisitRow {
    let visitRow = {
      visit: visit,
      park: visit.parkId,
      dogs: visit.visitorDogNames.toString(),
      selectedDogs: visit.visitorDogNames,
      arriving: visit.date + " at " + visit.time,
      action: "",
      editing: false
    }
    return visitRow;
  }

  public clearAndEdit(editingRow: VisitRow) {
    for (let visitRow of this.visitTable) {
      visitRow.editing = false;
    }
    this.editVisit(editingRow);
  }

  public editVisit(visitRow: VisitRow) {
    visitRow.editing = true;
    let visit = new Visit().fromJSON(visitRow.visit);
    this.dateControl = new FormControl(new Date(
      visit.date.getFullYear(),
      visit.date.getMonth(),
      visit.date.getDate(),
      visit.date.getHours(),
      visit.date.getMinutes(),
      0
    ));
  }

  public saveChanges(row: VisitRow) {
    if (!this.dateControl.valid) {
      this.handleError(new Error("Date is not valid"));
      return;
    }
    if (row.selectedDogs.length == 0) {
      this.handleError(new Error("You must select a Dog for this Visit"));
      return;
    }

    this.visitTable$.next(new Array<VisitRow>());
    let visit = new Visit().fromJSON(row.visit);
    visit.visitorDogNames = row.selectedDogs;
    let date = new Date(this.dateControl.value);
    visit.date = date;
    visit.time = date.getHours() + ":" + date.getMinutes();
    visit = visit.finalize();
    row.editing = false;

    this._calendarService.editVisit(visit).subscribe(
      (response) => {

        if (response.statusCode >= 400) {
          this.handleError(new Error(response.message));
        } else {
          this.updateVisitOnTable(row, visit);
          this.displayMessage("Visit with " + visit.visitorDogNames.toString() +
            " on " + visit.date + " at " + visit.time + " saved!");
          this._calendarService.refreshModel$.next(null);
        }
      },
      (error) => {
        this.handleError(error);
        row.editing = false;
      }
    )
  }

  /**
   * Recursive method to fetch all Parks, but uses a buffer to ensure
   * that parks are not retrieved multiple times
   *
   * @param visitRows
   */
  private fetchParkNames(visitRows: Array<VisitRow>, buffer: Map<string, BarkPark>) {
    if (visitRows.length == 0) {
      this.visitTable$.next(this.visitTable);
      return;
    }

    let visitRow = visitRows[0];
    let parkId = visitRow.visit.parkId;
    let bufferedPark = buffer.get(parkId);
    if (bufferedPark) { // Check buffer
      visitRow.park = bufferedPark.name;
      this.fetchParkNames(visitRows.slice(1, visitRows.length), buffer);
      this.addVisitRowToTable(visitRow);

    } else { // Need to ask server
      this._appService.fetchPark(parkId).subscribe(
        (response) => {
          if (response.statusCode >= 400) {
            this.handleError(response.message);

          } else { // Fetch successful
            let barkPark = new BarkPark().fromJSON(response.message);
            visitRow.park = barkPark.name;
            buffer.set(parkId, barkPark);
            this.addVisitRowToTable(visitRow);
          }
          this.fetchParkNames(visitRows.slice(1, visitRows.length), buffer);
        },
        (error) => {
          this.handleError(error);
          this.fetchParkNames(visitRows.slice(1, visitRows.length), buffer);
        }
      )
    }
  }

  public deleteVisit(visitRow: VisitRow) {
    this.visitTable = new Array<VisitRow>();
    this.visitTable$.next(this.visitTable);
    let visitId = visitRow.visit.visitId;
    let visitIds = this.user.userProfile.visitIds;

    this._calendarService.removeVisit(visitRow.visit.parkId, visitId).subscribe(
      (response) => {
        if (response.statusCode >= 400) {
          this.handleError(new Error(response.message));
        } else {
          this.displayMessage("Visit on " + visitRow.arriving + " removed");
          visitIds = visitIds.splice(visitIds.indexOf(visitId));
          this.updateVisits(this.user);
          this._calendarService.refreshModel$.next(null);
        }
      },
      (error) => {
        this.handleError(error);
      }
    )
  }

  public getId(visitRow: VisitRow, elementType: string): string {
    return visitRow.visit.visitId + "-" + elementType;
  }
}

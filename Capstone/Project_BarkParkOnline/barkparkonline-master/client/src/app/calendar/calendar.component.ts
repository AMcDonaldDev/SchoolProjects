import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
  OnInit,
} from '@angular/core';
import {
  startOfDay,
  endOfDay,
  subDays,
  addDays,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
} from 'date-fns';
import { Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
import { VisitScheduleFormComponent } from '../visit-schedule-form/visit-schedule-form.component';
import { BarkPark } from './bark-park';
import { AppService } from '../app-service';
import { Visit } from './visit';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { VisitScheduler } from './visit-scheduler/visit-scheduler.component';
import { CalendarService } from './calendar.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BasicUser } from '../sign-up/classes/basic-user';

const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

export interface CalendarMonthModel {
  visits: Array<Visit>;
  startDate: any;
  endDate: any;
}

const limit = 10;

@Component({
  selector: 'app-calendar-component',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['calendar.component.css'],
  templateUrl: 'calendar.component.html',
})
export class CalendarComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  user: BasicUser;

  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  offset: number = 0;
  viewDate: Date = new Date();
  sort: string = 'name';
  parkList = new Array<BarkPark>();
  currentPark: BarkPark;
  items = Array.from({ length: 100000 }).map((_, i) => `Item #${i}`);

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fas fa-fw fa-pencil-alt"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      },
    },
    {
      label: '<i class="fas fa-fw fa-trash-alt"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter((iEvent) => iEvent !== event);
        this.handleEvent('Deleted', event);
      },
    },
  ];

  events: CalendarEvent[] = [];

  activeDayIsOpen: boolean = true;

  constructor(
    private _modal: NgbModal,
    private _appService: AppService,
    private _bottomSheet: MatBottomSheet,
    private _calendarService: CalendarService,
    private _snackBar: MatSnackBar
  ) {
    this.currentPark = new BarkPark();
    this.currentPark.name = "My Favorite Park";
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  ngOnInit(): void {
    this._appService
      .fetchParks(this.offset, limit, this.sort)
      .subscribe((data) => {
        for (let park of data) {
          let barkPark = new BarkPark().fromJSON(park);
          this.parkList.push(barkPark);
          this.offset += limit;
        }
      });

    // Fetch an update when refreshModel has next()
    this._calendarService.refreshModel$.subscribe(
      () => {
        this.events.splice(0, this.events.length);
        if (this.currentPark._id) {
          this.switchPark(this.currentPark);
        }
      }
    )

    this._appService.login$.subscribe(
      (user) => {
        this.user = user;
        this._appService.loggedIn = true;
      }
    )
  }

  eventTimesChanged({
    event,
    newStart,
    newEnd,
  }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
    this._modal.open(this.modalContent, { size: 'lg' });
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: 'New event',
        start: startOfDay(new Date()),
        end: endOfDay(new Date()),
        color: colors.red,
        draggable: false,
        resizable: {
          beforeStart: false,
          afterEnd: false,
        },
      },
    ];
  }

  public switchPark(park: BarkPark) {
    this.events = new Array<CalendarEvent>();
    this.currentPark = park;
    this._calendarService.selectedParkId = park._id;
    this._calendarService.selectedPark = park;
    let year = this.viewDate.getFullYear();
    let month = this.viewDate.getMonth() + 1;

    this._calendarService.getCalendar(park._id, month, year).subscribe(
      (response) => {
        if (response.statusCode >= 400) {
          this.handleError(new Error(response.message));
        }
        else {
          let model = <CalendarMonthModel>response.message;
          if (model.visits.length == 0) {
            this.displayMessage(park.name + " has no visits this month");
          }
          else {
            this.parseVisitsToEvents(model.visits);
          }
        }
      },
      (error) => {
        this.handleError(error);
      }
    )
  }

  public parseVisitsToEvents(visits: Array<Visit>) {
    for (let v of visits) {
      let visit = new Visit().fromJSON(v);
      let event = <CalendarEvent>{};
      event.title = visit.visitorFullName + " w/ " + visit.visitorDogNames + " at " + visit.time;
      event.start = new Date(
        visit.date.getFullYear(),
        visit.date.getMonth(),
        visit.date.getDate(),
        visit.date.getHours(),
        visit.date.getMinutes(),
        0
      );
      event.end = event.start;
      event.end.setHours(event.end.getHours() + 1);
      this.events.push(event);
    }
    this._calendarService.refresh.next(new Subject());
  }

  public displayMessage(msg: string) {
    this._snackBar.open(msg, null, { duration: 2000 });
  }

  private handleError(error: Error) {
    this.displayMessage("Error: " + error.message);
  }

  addEventModal(): void {
    const modalRef = this._modal.open(VisitScheduleFormComponent, {
      size: 'lg',
    });
    modalRef.componentInstance as VisitScheduleFormComponent;
    modalRef.closed.subscribe((data) => {
      alert(data);
    });
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  public getSize() {
    alert(this.parkList.length);
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  openVisitScheduler() {
    this._bottomSheet.open(VisitScheduler);
  }

  refreshCalendar(): Subject<any> {
    return this._calendarService.refresh;
  }

  loggedIn(): boolean {
    return this._appService.loggedIn;
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment as env } from 'src/environments/environment';
import { ApiResponse } from '../api-response';
import { BarkPark } from './bark-park';
import { Visit } from './visit';

@Injectable({
  providedIn: 'root',
})
export class CalendarService {

  public selectedParkId: string = "";
  public selectedPark: BarkPark;
  public refresh: Subject<any> = new Subject();

  public refreshModel$: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(
    private http: HttpClient

  ) {
    this.selectedPark = new BarkPark();
  }

  public getOneVisit(visitId: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(env.hostUrl + "calendar/scheduling/visit/" + visitId);
  }

  public getManyVisits(visitIds: Array<string>): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(env.hostUrl + "calendar/scheduling/visit/multiple", visitIds);
  }

  public addVisit(visit: Visit): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(env.hostUrl + "calendar/scheduling/visit/create", visit);
  }

  public removeVisit(parkId: string, visitId: string): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(env.hostUrl +
      "calendar/scheduling/visit/remove/" + visitId +
      this.injectParkId(parkId));
  }

  private injectParkId(parkId: string): string {
    if (parkId) {
      return "/?parkId=" + parkId;
    }
    return "";
  }

  public editVisit(visit: Visit): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(env.hostUrl + "calendar/scheduling/visit/edit/" +
      visit.visitId, visit);
  }

  public getCalendar(parkId: string, month: number, year: number) {
    return this.http.get<ApiResponse>(env.hostUrl + "calendar/" + year + "/" + month +
      "/?id=" + parkId);
  }
}

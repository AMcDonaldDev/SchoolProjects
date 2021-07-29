import { enableProdMode, NgModule } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { MaterialsModule } from './app/materials.module';
import { environment } from './environments/environment';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatNativeDateModule } from '@angular/material/core';
import { AppComponent } from './app/app.component';
import { LoginComponent } from './app/login/login.component';
import { MainComponent } from './app/main/main.component';
import { SignUpComponent } from './app/sign-up/sign-up.component';
import { ProfileComponent } from './app/profile/profile.component';
import { LogoutComponent } from './app/logout/logout.component';
import { AppService } from './app/app-service';
import { LogoutResolver } from './app/logout/logout.resolver';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Interceptor } from './app/interceptor';
import { PhoneNumberInput, TelInput } from './app/sign-up/phone-number-input/phone-number-input.component';
import { CalendarComponent } from './app/calendar/calendar.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { AppRoutingModule } from './app/app-routing.module';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { VisitScheduleFormComponent } from './app/visit-schedule-form/visit-schedule-form.component'
import { VisitScheduler } from './app/calendar/visit-scheduler/visit-scheduler.component';
import { ValidationService } from './app/validation/validation.service';
import { CalendarService } from './app/calendar/calendar.service';
import { NgxMatMomentModule } from '@angular-material-components/moment-adapter';

if (environment.production) {
  enableProdMode();
}

// Default MatFormField appearance to 'fill' as that is the new recommended approach and the
// `legacy` and `standard` appearances are scheduled for deprecation in version 10.
// This makes the examples that use MatFormField render the same in StackBlitz as on the docs site.
@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MaterialsModule,
    CalendarModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NgbDropdownModule,
    NgxMatMomentModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory })
  ],
  entryComponents: [
    AppComponent,
    VisitScheduler
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    SignUpComponent,
    ProfileComponent,
    LogoutComponent,
    PhoneNumberInput,
    TelInput,
    CalendarComponent,
    VisitScheduleFormComponent,
    VisitScheduler
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'fill' } },
    CalendarService,
    ValidationService,
    LogoutResolver,
    MatDatepickerModule,
    MatNativeDateModule,
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
  ]
})
export class AppModule {}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

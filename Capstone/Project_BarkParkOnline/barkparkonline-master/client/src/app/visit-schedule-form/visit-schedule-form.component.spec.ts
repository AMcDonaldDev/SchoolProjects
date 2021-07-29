import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitScheduleFormComponent } from './visit-schedule-form.component';

describe('VisitScheduleFormComponent', () => {
  let component: VisitScheduleFormComponent;
  let fixture: ComponentFixture<VisitScheduleFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VisitScheduleFormComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitScheduleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhQuizDetailsComponent } from './rh-quiz-details.component';

describe('RhQuizDetailsComponent', () => {
  let component: RhQuizDetailsComponent;
  let fixture: ComponentFixture<RhQuizDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhQuizDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhQuizDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

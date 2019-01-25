import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhQuizzResultsComponent } from './rh-quizz-results.component';

describe('RhQuizzResultsComponent', () => {
  let component: RhQuizzResultsComponent;
  let fixture: ComponentFixture<RhQuizzResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhQuizzResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhQuizzResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

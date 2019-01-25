import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateQuizzComponent } from './candidate-quizz.component';

describe('CandidateQuizzComponent', () => {
  let component: CandidateQuizzComponent;
  let fixture: ComponentFixture<CandidateQuizzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateQuizzComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateQuizzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

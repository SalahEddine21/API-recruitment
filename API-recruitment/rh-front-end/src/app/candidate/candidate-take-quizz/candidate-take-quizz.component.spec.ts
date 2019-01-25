import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateTakeQuizzComponent } from './candidate-take-quizz.component';

describe('CandidateTakeQuizzComponent', () => {
  let component: CandidateTakeQuizzComponent;
  let fixture: ComponentFixture<CandidateTakeQuizzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateTakeQuizzComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateTakeQuizzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

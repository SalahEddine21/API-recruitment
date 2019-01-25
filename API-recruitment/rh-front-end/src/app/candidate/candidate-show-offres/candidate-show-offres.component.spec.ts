import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateShowOffresComponent } from './candidate-show-offres.component';

describe('CandidateShowOffresComponent', () => {
  let component: CandidateShowOffresComponent;
  let fixture: ComponentFixture<CandidateShowOffresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateShowOffresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateShowOffresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateOffreDetailsComponent } from './candidate-offre-details.component';

describe('CandidateOffreDetailsComponent', () => {
  let component: CandidateOffreDetailsComponent;
  let fixture: ComponentFixture<CandidateOffreDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateOffreDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateOffreDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

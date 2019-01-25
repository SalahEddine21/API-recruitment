import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateProfilComponent } from './candidate-profil.component';

describe('CandidateProfilComponent', () => {
  let component: CandidateProfilComponent;
  let fixture: ComponentFixture<CandidateProfilComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateProfilComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

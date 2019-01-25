import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhCandidatesCvsComponent } from './rh-candidates-cvs.component';

describe('RhCandidatesCvsComponent', () => {
  let component: RhCandidatesCvsComponent;
  let fixture: ComponentFixture<RhCandidatesCvsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhCandidatesCvsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhCandidatesCvsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

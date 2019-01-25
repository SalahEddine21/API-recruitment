import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhOffreCandidaturesComponent } from './rh-offre-candidatures.component';

describe('RhOffreCandidaturesComponent', () => {
  let component: RhOffreCandidaturesComponent;
  let fixture: ComponentFixture<RhOffreCandidaturesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhOffreCandidaturesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhOffreCandidaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

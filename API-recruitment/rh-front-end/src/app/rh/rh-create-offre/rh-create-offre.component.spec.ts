import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhCreateOffreComponent } from './rh-create-offre.component';

describe('RhCreateOffreComponent', () => {
  let component: RhCreateOffreComponent;
  let fixture: ComponentFixture<RhCreateOffreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhCreateOffreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhCreateOffreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

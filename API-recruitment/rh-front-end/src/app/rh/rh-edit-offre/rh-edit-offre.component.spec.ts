import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhEditOffreComponent } from './rh-edit-offre.component';

describe('RhEditOffreComponent', () => {
  let component: RhEditOffreComponent;
  let fixture: ComponentFixture<RhEditOffreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhEditOffreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhEditOffreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

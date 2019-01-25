import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhShowOffreComponent } from './rh-show-offre.component';

describe('RhShowOffreComponent', () => {
  let component: RhShowOffreComponent;
  let fixture: ComponentFixture<RhShowOffreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhShowOffreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhShowOffreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

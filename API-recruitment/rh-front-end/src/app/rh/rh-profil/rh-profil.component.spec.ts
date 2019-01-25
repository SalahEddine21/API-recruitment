import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhProfilComponent } from './rh-profil.component';

describe('RhProfilComponent', () => {
  let component: RhProfilComponent;
  let fixture: ComponentFixture<RhProfilComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhProfilComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

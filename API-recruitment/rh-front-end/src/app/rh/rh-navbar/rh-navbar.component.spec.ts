import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhNavbarComponent } from './rh-navbar.component';

describe('RhNavbarComponent', () => {
  let component: RhNavbarComponent;
  let fixture: ComponentFixture<RhNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

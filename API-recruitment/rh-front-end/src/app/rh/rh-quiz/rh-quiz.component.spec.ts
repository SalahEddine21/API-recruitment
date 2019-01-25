import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhQuizComponent } from './rh-quiz.component';

describe('RhQuizComponent', () => {
  let component: RhQuizComponent;
  let fixture: ComponentFixture<RhQuizComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhQuizComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

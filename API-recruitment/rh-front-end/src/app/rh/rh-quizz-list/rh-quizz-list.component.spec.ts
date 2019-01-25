import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RhQuizzListComponent } from './rh-quizz-list.component';

describe('RhQuizzListComponent', () => {
  let component: RhQuizzListComponent;
  let fixture: ComponentFixture<RhQuizzListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RhQuizzListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RhQuizzListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

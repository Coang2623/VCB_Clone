import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavLevel3Component } from './nav-level-3.component';

describe('NavLevel3Component', () => {
  let component: NavLevel3Component;
  let fixture: ComponentFixture<NavLevel3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavLevel3Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavLevel3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

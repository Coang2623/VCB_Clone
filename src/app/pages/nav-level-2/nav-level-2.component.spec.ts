import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavLevel2Component } from './nav-level-2.component';

describe('NavLevel2Component', () => {
  let component: NavLevel2Component;
  let fixture: ComponentFixture<NavLevel2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavLevel2Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavLevel2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

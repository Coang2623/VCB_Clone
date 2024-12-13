import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersionalLoginComponent } from './persional-login.component';

describe('PersionalLoginComponent', () => {
  let component: PersionalLoginComponent;
  let fixture: ComponentFixture<PersionalLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersionalLoginComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersionalLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

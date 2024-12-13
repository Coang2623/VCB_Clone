import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaNhanComponent } from './ca-nhan.component';

describe('CaNhanComponent', () => {
  let component: CaNhanComponent;
  let fixture: ComponentFixture<CaNhanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaNhanComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaNhanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

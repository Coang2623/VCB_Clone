import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardArticleComponent } from './dashboard-article.component';

describe('DashboardArticleComponent', () => {
  let component: DashboardArticleComponent;
  let fixture: ComponentFixture<DashboardArticleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardArticleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

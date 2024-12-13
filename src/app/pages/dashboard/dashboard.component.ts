import {Component, OnInit} from '@angular/core';
import {DashboardSideBarComponent} from "./dashboard-side-bar/dashboard-side-bar.component";
import {DashboardNavComponent} from "./dashboard-nav/dashboard-nav.component";
import {DashboardMainComponent} from "./dashboard-main/dashboard-main.component";
import {ActivatedRoute, NavigationEnd, Router, RouterOutlet} from "@angular/router";
import {filter} from "rxjs";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    DashboardSideBarComponent,
    DashboardNavComponent,
    DashboardMainComponent,
    RouterOutlet
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  activeChildRoute: string = '';

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.router.navigate(['/dashboard/Users']);

    // Lắng nghe sự kiện navigation và cập nhật activeChildRoute
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        const child = this.activatedRoute.firstChild;
        if (child) {
          child.url.subscribe((segments) => {
            this.activeChildRoute = segments.map(segment => segment.path).join('/');
          });
        }
      });
  }

}

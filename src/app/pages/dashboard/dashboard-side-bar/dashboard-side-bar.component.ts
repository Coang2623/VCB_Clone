import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {AuthService} from "../../../services/auth-service/auth.service";

@Component({
  selector: 'app-dashboard-side-bar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './dashboard-side-bar.component.html',
  styleUrl: './dashboard-side-bar.component.css'
})

export class DashboardSideBarComponent implements OnInit{
  @Input() activeLink: string = 'Users';

  constructor(private router: Router, private authService: AuthService, private activatedRoute: ActivatedRoute) {
  }

  sideBarList: string[] = ["Users", "Articles"];
  ngOnInit(): void {
  }

  changeActiveLink(link: string) {
    this.activeLink = link;
  }

  onLogout(event: Event) {
    event.preventDefault()
    const body = {
      token: localStorage.getItem('authToken')
    }
    this.authService.logout(body).subscribe({
      next: (value) => {
        console.log('Logout successfully');
      }
    });
    localStorage.removeItem('authToken');
    body.token = '';
    this.router.navigate(['/auth']);
  }
}

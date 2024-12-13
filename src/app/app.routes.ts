import { Routes } from '@angular/router';
import { LayoutComponent } from './pages/layout/layout.component';
import {PersionalLoginComponent} from "./pages/persional-login/persional-login.component";
import {DashboardComponent} from "./pages/dashboard/dashboard.component";
import {DashboardUserComponent} from "./pages/dashboard/dashboard-user/dashboard-user.component";
import {DashboardMainComponent} from "./pages/dashboard/dashboard-main/dashboard-main.component";
import {authGuard} from "./guards/auth.guard";
import {UnauthorizedComponent} from "./pages/unauthorized/unauthorized.component";
import {CaNhanComponent} from "./pages/ca-nhan/ca-nhan.component";
import {TimKiemComponent} from "./pages/tim-kiem/tim-kiem.component";
import {DashboardArticleComponent} from "./pages/dashboard/dashboard-article/dashboard-article.component";

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        component: CaNhanComponent
      },
      {
        path: 'Home',
        component: CaNhanComponent
      },
      {
        path: 'Tim-kiem',
        component: TimKiemComponent,
        data: {background: 'light'}
      }
    ]
  },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
  },
  {
    path: 'auth',
    component: PersionalLoginComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_USER_MANAGER', 'ROLE_ARTICLE_MANAGER']},
    children: [
      {
        path: 'Users',
        component: DashboardUserComponent,
        canActivate: [authGuard],
        data: {roles: ['ROLE_ADMIN', 'ROLE_USER_MANAGER']},
      },
      {
        path: 'Articles',
        component: DashboardArticleComponent,
        canActivate: [authGuard],
        data: {roles: ['ROLE_ADMIN', 'ROLE_ARTICLE_MANAGER']},
      }
    ]
  }
];

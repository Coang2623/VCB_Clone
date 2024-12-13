import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet} from '@angular/router';
import {TranslateService} from "@ngx-translate/core";

// @ts-ignore
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  title: string = 'vcb-clone';

  constructor(private translate: TranslateService) {
    this.translate.addLangs(['vi', 'en']);
    this.translate.setDefaultLang('en');
    this.translate.use('en');
  }
}

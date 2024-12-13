import {Component, Input, OnInit} from '@angular/core';
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-ca-nhan',
  standalone: true,
  imports: [
    TranslatePipe,
    FormsModule
  ],
  templateUrl: './ca-nhan.component.html',
  styleUrl: './ca-nhan.component.css'
})
export class CaNhanComponent implements OnInit {

  @Input() language: string = navigator.language;
  searchValue: string = '';

  constructor(private translate: TranslateService, private router: Router) {
    this.translate.addLangs(['vi', 'en']);
    this.translate.setDefaultLang(this.language);
    this.translate.use(this.language);
  }

  onSearch(): void {
    if (this.searchValue) {
      this.router.navigate(['/Tim-kiem'], { queryParams: { keyword: this.searchValue } });
    }
  }

  searchDialogOpen: boolean = false;

  dayOrNight: string = "morning";
  now = new Date();
  ngOnInit(): void {
    const now = new Date();
    if(now.getHours() >= 6 && now.getHours() < 12){
      this.dayOrNight = "morning";
    }else if (now.getHours() >= 12 && now.getHours() < 18){
      this.dayOrNight = "afternoon";
    }else{
      this.dayOrNight = "night";
    }
  }
}

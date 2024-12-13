import { NavLevel2Component } from '../nav-level-2/nav-level-2.component';
import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';

import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {ActivatedRoute, Event, NavigationEnd, Router, RouterOutlet} from "@angular/router";
import {filter} from "rxjs";

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [FormsModule, CommonModule, NavLevel2Component, TranslateModule, RouterOutlet],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent implements OnInit{
  @ViewChild(NavLevel2Component) navLevel2 !: NavLevel2Component;
  isOpen: Boolean = false;
  language: string = navigator.language;

  constructor(private translate: TranslateService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.translate.addLangs(['vi', 'en']);
    this.translate.setDefaultLang(this.language);
    this.translate.use(this.language);
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: Event): void {
    this.navLevel2.level_2.isShow = false;// Ẩn sub menu khi click ra ngoài
    this.isOpen = false;
  }

  changeLevel2(lv_2: string, event: MouseEvent) {
    event.stopPropagation();
    if(lv_2 === this.navLevel2.level_2.case) {
      this.navLevel2.level_2.isShow = !this.navLevel2.level_2.isShow;
    }else{
      this.navLevel2.level_2.case = lv_2;
      this.navLevel2.level_2.isShow = true;
    }
    this.isOpen = this.navLevel2.level_2.isShow;
  }

  loginDrop: boolean = false;

  switchLanguage(language: string) {
    this.language = language;
    this.translate.use(this.language);
  }

  langDrop:boolean = false;
  smNavDrop: boolean = false;
  @HostListener('window:resize', ['$event'])
  onResize(event: UIEvent) {
    const  width = (event.target as Window).innerWidth;
    if(width >= 1200){
      this.smNavDrop = false;
    }
  }
  smMenuDrop: boolean = false;
  smLangDrop: boolean = false;

  backgroundClass: string = 'bg-default';

  ngOnInit(): void {
    // Lắng nghe NavigationEnd để cập nhật nền
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        this.setBackgroundClass();
      });

    // Gọi cập nhật ngay khi component được khởi tạo
    this.setBackgroundClass();
  }

  private setBackgroundClass(): void {
    // Tìm route hiện tại (last active route)
    let currentRoute = this.activatedRoute;
    while (currentRoute.firstChild) {
      currentRoute = currentRoute.firstChild;
    }

    // Lấy thông tin từ `data`
    const background = currentRoute.snapshot.data['background'];
    if (background === 'light') {
      this.backgroundClass = 'bg-light';
    } else {
      this.backgroundClass = 'bg-default';
    }
  }
}

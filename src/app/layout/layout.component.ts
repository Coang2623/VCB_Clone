import { NavLevel2Component } from './../nav-level-2/nav-level-2.component';
import { Component, inject, signal, ViewChild } from '@angular/core';
import { CaNhanComponent } from '../ca-nhan/ca-nhan.component';
import { NavLevel3Component } from "../nav-level-3/nav-level-3.component";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CaNhanComponent, NavLevel3Component, FormsModule, CommonModule, NavLevel2Component],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {
  @ViewChild(NavLevel2Component) navLevel2 !: NavLevel2Component;
  isOpen: Boolean = false;

  changeLevel2(lv_2: string) {
    console.log(lv_2);
    if(lv_2 === this.navLevel2.level_2.case) {
      this.navLevel2.level_2.isShow = !this.navLevel2.level_2.isShow;
    }else{
      this.navLevel2.level_2.case = lv_2;
      this.navLevel2.level_2.isShow = true;
    }
    this.isOpen = this.navLevel2.level_2.isShow;
  }


}

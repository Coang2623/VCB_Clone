import {Component, HostListener, ViewChild} from '@angular/core';
import { NavLevel3Component } from "../nav-level-3/nav-level-3.component";

@Component({
  selector: 'app-nav-level-2',
  standalone: true,
  imports: [NavLevel3Component],
  templateUrl: './nav-level-2.component.html',
  styleUrl: './nav-level-2.component.css'
})
export class NavLevel2Component {
  level_2 : {
    case: string,
    isShow: Boolean
  } = {
    case: "",
    isShow: true
  }

  @ViewChild (NavLevel3Component) navLevel3 !: NavLevel3Component;

  changeLevel3(lv_3: string) {
    this.navLevel3.level_3 = lv_3;
  }
}

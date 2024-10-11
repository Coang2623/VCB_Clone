import { Component } from '@angular/core';
import { CaNhanComponent } from '../ca-nhan/ca-nhan.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CaNhanComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

}

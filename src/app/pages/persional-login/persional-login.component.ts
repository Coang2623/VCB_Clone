import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../../services/auth-service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-persional-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './persional-login.component.html',
  styleUrl: './persional-login.component.css'
})
export class PersionalLoginComponent implements OnInit{

  constructor(private authService : AuthService, private router: Router) {
  }

  dayOrNight = true;
  username = '';
  password = '';

  btnLoginOnclick(){
    this.authService.login(this.username, this.password).subscribe({
      next: (value) => {localStorage.setItem('authToken', value.result.token); this.router.navigate(['/dashboard/']);},
      error: (error) => {console.log(error);},
    });
  }

  ngOnInit(): void {
    const now = new Date();
    console.log(now);
    if(now.getHours() >= 18){
      this.dayOrNight = false;
    }
  }
}

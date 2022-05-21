import { Injectable } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "./auth/auth.service";
import {timer} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TimerRedirectLoginService {

  timeLeft : number = 5;

  constructor() { }

  observableTimer(): void{
    const source = timer(1000, 2000);
    source.subscribe(val => {
      this.timeLeft = this.timeLeft - val;
      if(this.timeLeft < 0){
        this.redirectToLogin();
      }
    });
  }

  redirectToLogin(): void {
    window.location.href = '/login'; // navigate to other page
  }
}

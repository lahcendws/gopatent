import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {timer} from "rxjs";
import {TimerRedirectLoginService} from "../../services/timer-redirect-login.service";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
  message: string = '';

  forgotPasswordForm: FormGroup = new FormGroup({
    email: new FormControl('')
  });

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private timerRedirectloginService: TimerRedirectLoginService) { }

  ngOnInit(): void {
    this.forgotPasswordForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
      }
    );
  }

  onSubmit(): void {
    this.timerRedirectloginService.observableTimer(); // start timer for redirect to login

    this.message = 'Un mail vient de vous être envoyé, pour réinitialiser votre mot de passe.';
    const { email } = this.forgotPasswordForm.value;

    if (this.forgotPasswordForm.invalid) {
      return;
    } else {
      this.authService.resetPassword(email).subscribe();
    }
  }
}

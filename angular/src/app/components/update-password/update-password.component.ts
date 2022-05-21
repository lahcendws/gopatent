import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";
import ValidationRegistration from "../registration/utils/validationRegistration";
import {TimerRedirectLoginService} from "../../services/timer-redirect-login.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.scss']
})
export class UpdatePasswordComponent implements OnInit {

  submitted = false;
  isSignUpFailed = false;
  validMessage: string = '';
  errorMessage: string = '';
  token: string = '';
  message: string = '';

  formUpdatePassword: FormGroup = new FormGroup({
    password: new FormControl(''),
    confirmPassword: new FormControl('')
  });

  constructor(private route:ActivatedRoute, private formBuilder: FormBuilder, private authService: AuthService, private timerRedirectloginService: TimerRedirectLoginService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{this.token = params['token']});

    this.formUpdatePassword = this.formBuilder.group(
      {
        password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(40)]],
        confirmPassword: ['', [Validators.required]]
      },
      {
        validators: [ValidationRegistration.match('password', 'confirmPassword')]
      }
    );
  }

  /* Submite for update password */
  onSubmit(): void {
    const { password } = this.formUpdatePassword.value;

    this.submitted = true;
    if (this.formUpdatePassword.invalid) {
      return;
    } else {
      this.authService.updatePassword(password, this.token).subscribe(
        data => {
          this.submitted = true;
          this.isSignUpFailed = false;
          this.validMessage = data.message;
          if (this.validMessage != ''){
            /* message for success */
            Swal.fire({
              title: 'Félicitations!',
              text: this.validMessage,
              icon: 'success',
            })
            this.timerRedirectloginService.observableTimer(); // start timer for redirect to login
          }
        },
        err => {
          if (err.error == null) {
            this.errorMessage = 'Une erreur est survenue, veuillez réessayer plus tard';
          } else {
            this.errorMessage = err.error.message;
          }
          this.isSignUpFailed = true;
        }
      );
    }
  }
}

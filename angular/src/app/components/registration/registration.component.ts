import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import ValidationRegistration from "./utils/validationRegistration";
import { AuthService } from "../../services/auth/auth.service";
import {Router} from "@angular/router";
import {TokenStorageService} from "../../services/auth/token-storage.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  form: FormGroup = new FormGroup({
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl('')
  });
  submitted = false;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  isLoggedIn = false;
  isLoginFailed = false;
  roles: string[] = [];

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private route:Router, private tokenStorage: TokenStorageService) {}

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.redirectToProfileEdit();
    }
    this.form = this.formBuilder.group(
      {
        firstname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
        lastname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(40)]],
        confirmPassword: ['', [Validators.required]]
      },
      {
        validators: [ValidationRegistration.match('password', 'confirmPassword')]
      }
    );
  }

  onSubmit(): void {
    const { firstname, lastname, email, password } = this.form.value;

    this.submitted = true;
    if (this.form.invalid) {
      return;
    } else {
      /* when submite and form is valid call service */
      this.authService.register(firstname, lastname, email, password).subscribe(
        data => {
          this.isSuccessful = true;
          this.isSignUpFailed = false;

          /* login and redirect */
          this.tokenStorage.saveToken(data.token);
          this.tokenStorage.saveUser(data);

          this.isLoginFailed = false;
          this.isLoggedIn=true;
          this.isLoggedIn = this.authService.isLoggedIn();
          this.roles = this.tokenStorage.getUser().roles;

          this.redirectToProfileEdit();
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

  redirectToProfileEdit(): void{
    window.location.href = '/user/profile/edit'; // navigate to other page
  }
}

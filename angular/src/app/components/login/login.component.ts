import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {TokenStorageService} from "../../services/auth/token-storage.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  submitted = false;
  isAdmin=false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
      this.redirectToProfileEdit();
    }
    this.form = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(40)]],
      }
    );
  }

  onSubmit(): void {
    const { email, password } = this.form.value;

    this.submitted = true;
    /* when submite and form is valid call service */
    this.authService.login(email, password).subscribe(
      user => {
        this.tokenStorage.saveToken(user.token);
        this.tokenStorage.saveUser(user);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;

        if(this.roles.toString() =="ROLE_ADMIN"){
          this.isAdmin=true;
        }
        this.redirectToProfileEdit();
      },
      err => {
        if (err.error == null) {
          this.errorMessage = 'Une erreur est survenue, verifiez vos identifiants';
        } else {
          this.errorMessage = err.error.message;
        }
        this.isLoginFailed = true;
      }
    );
  }

  redirectToProfileEdit(): void {
    window.location.href = '/user/profile/edit'; // navigate to other page
  }
}

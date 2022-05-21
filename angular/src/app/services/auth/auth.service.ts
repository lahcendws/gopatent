import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {TokenStorageService} from './token-storage.service';
import {environment} from "../../../environments/environment";

const AUTH_API = environment.AUTH_API;

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
  }

  /* connection send on port 8080 */
  login(email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'login', {
      email,
      password
    }, httpOptions);
  }

  /* register send on port 8080 */
  register(firstname: string, lastname: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'register', {
      firstname,
      lastname,
      email,
      password
    }, httpOptions);
  }

  isAdmin(): boolean {
    if (this.tokenStorage.getUser().roles == "ROLE_ADMIN")
      return true;
    return false;
  }

  isLoggedIn(): boolean {
    if (this.tokenStorage.getUser().roles == "ROLE_USER" || this.tokenStorage.getUser().roles == "ROLE_ADMIN")
      return true;
    return false;
  }

  /* reset password send on port 8080 */
  resetPassword(email: string): Observable<any> {
    return this.http.post(AUTH_API + 'user/resetPassword', {
      email
    }, httpOptions);
  }

  /* update password send on port 8080 for save */
  updatePassword(password: string, token: string): Observable<any> {
    return this.http.post(AUTH_API + 'user/saveResetPassword', {
      password,
      token
    }, httpOptions);
  }
}

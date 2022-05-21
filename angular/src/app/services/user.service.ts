import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/user';
import {AuthService} from './auth/auth.service';
import {TokenStorageService} from './auth/token-storage.service';
import {environment} from "../../environments/environment";


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  USER_API: string = environment.USER_API;
  users?: User[];
  public roles: string[] = [];

  constructor(private http: HttpClient) {
  }

  /**
   * Get all users
   * admin only
   */
  listUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.USER_API}/users`)
  }

  /**
   * Delete user
   * @param id
   */
  deleteUser(id: number): Observable<any>  {
    return this.http.delete(`${this.USER_API}/users/${id}`);
  }

  /**
   * Retrieve the user profile by userId
   */
  public getUserConnected(): Observable<User> {
    return this.http.get<User>(`${this.USER_API}/profile`)
  }

  /**
   * Update the user profile
   */
  public updateUser(user?: User): Observable<User> {
    return this.http.put<User>(`${this.USER_API}/update`, user);
  }
}

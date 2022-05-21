import { Component, Input, OnInit } from '@angular/core';
import { TokenStorageService } from "../../services/auth/token-storage.service";
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  isAdmin=false
  currentUser: any;
  innerwidth: any;

  constructor(private tokenStorageService: TokenStorageService, private authService:AuthService) { }

  ngOnInit(): void {
    /* ensures the resulting type is a boolean */
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.isAdmin = this.authService.isAdmin();
    this.currentUser = this.tokenStorageService.getUser();
    this.innerwidth = window.innerWidth;
  }

  /* Clear session  */
  logout(): void {
    this.tokenStorageService.signOut();
    window.location.href = '/login';
  }
}

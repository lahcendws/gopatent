import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from "../../services/auth/token-storage.service";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  isLoggedIn = false;
  currentUser: any;
  constructor(private tokenStorageService: TokenStorageService, private authService:AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.currentUser = this.tokenStorageService.getUser();
  }

}

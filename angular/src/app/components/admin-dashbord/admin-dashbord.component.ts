import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import {TokenStorageService} from "../../services/auth/token-storage.service";

@Component({
  selector: 'app-admin-dashbord',
  templateUrl: './admin-dashbord.component.html',
  styleUrls: ['./admin-dashbord.component.scss'],
})
export class AdminDashbordComponent implements OnInit {
  users: User[] = [];
  page = 1;
  currentUser?: User;

  constructor(private tokenStorage: TokenStorageService,
              private userService: UserService) {}

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.currentUser = this.tokenStorage.getUser();
      this.getAllUsers();
    }
  }

  getAllUsers() {
    this.userService.listUsers().subscribe(
      (users) => {
      this.users = users;
    });
  }

  /**
   * Method to delete a user
   */
  deleteUser(user: User) {
    let conf = confirm('Vous êtes sur ?');
    if (conf) {
      this.userService.deleteUser(user.id).subscribe(
        () => {
          window.location.reload();
        }
      );
    }
  }

  handlePageChange(event: number) {
    this.page = event;
  }
}

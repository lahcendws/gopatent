import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { UserService } from '../../../services/user.service';
import { TokenStorageService } from '../../../services/auth/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../model/user';
import Swal from 'sweetalert2';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss'],
})
export class EditProfileComponent implements OnInit {
  @Output() change?: EventEmitter<MatSlideToggleChange>;
  @Input() checked?: boolean;

  editProfileForm: FormGroup;
  currentToken: any;
  user?: User;
  isChecked = true;
  toggle?: boolean;

  constructor(
    private tokenStorage: TokenStorageService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
    this.editProfileForm = this.formBuilder.group({
      firstname: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(40),
        ],
      ],
      lastname: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(40),
        ],
      ],
      companyName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(40),
        ],
      ],
      employed: [false],
      companyDomain: [false],
      createdCompany: [false],
    });
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.user = this.tokenStorage.getUser();
      this.currentToken = this.tokenStorage.getToken();
      if (this.user != undefined) this.readUserProfile();

      /*   this.readUserProfile();*/

      if (this.user != undefined) {
        this.user.employed = JSON.parse(
          <string>localStorage.getItem('toggleButtonEmployed')
        );
      }

      if (this.user != undefined) {
        this.user.createdCompany = JSON.parse(
          <string>localStorage.getItem('toggleButtonCreatedCompany')
        );
      }

      if (this.user != undefined) {
        this.user.companyDomain = JSON.parse(
          <string>localStorage.getItem('toggleButtonCompanyDomain')
        );
      }

      this.readUserProfile();
    } else {
      this.router.navigate(['/login']);
    }
  }

  fillProfil() {
    this.editProfileForm.controls['firstname']?.setValue(this.user?.firstname);
    this.editProfileForm.controls['lastname']?.setValue(this.user?.lastname);
    this.editProfileForm.controls['companyName']?.setValue(
      this.user?.companyName
    );
    this.editProfileForm.controls['createdCompany']?.setValue(
      this.user?.createdCompany
    );
    this.editProfileForm.controls['employed']?.setValue(this.user?.employed);
    this.editProfileForm.controls['companyDomain']?.setValue(
      this.user?.companyDomain
    );
    this.toggleCompanyNameValidator();
  }

  /**
   * Method created to get the user's data
   */
  readUserProfile() {
    this.userService.getUserConnected().subscribe(
      (userProfile) => {
        this.user = userProfile;

        this.fillProfil();
      },
      (error: HttpErrorResponse) => {
        // Error alert
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Erreur du serveur!',
        });
      }
    );
  }

  /**
   * Method created to update the user's data after submitting the form validated
   */
  onFormSubmit() {
    if (this.editProfileForm.valid) {
      this.userService.updateUser(this.editProfileForm.value).subscribe(
        (userProfile) => {
          Swal.fire({
            title: 'Félicitations!',
            text: 'Votre modification a été enregistrée',
            icon: 'success',
          });

          setTimeout(() => {
            this.router.navigate(['/user/profile/edit']).then(() => {
              window.location.reload();
            });
          }, 3000);
        },
        (error: HttpErrorResponse) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Erreur du serveur!',
          });
        }
      );
    }
  }

  /**
   * 3 methods created to set the state of 3 slide toggle buttons
   */
  onChangeEmployed(ob: MatSlideToggleChange) {
    if (this.user != null) {
      this.user.employed = !this.user.employed;
      localStorage.setItem(
        'toggleButtonEmployed',
        JSON.stringify(this.user?.employed)
      );
      this.toggleCompanyNameValidator();
    }
  }

  onChangeCreatedCompany(ob: MatSlideToggleChange) {
    if (this.user != null) {
      this.user.createdCompany = !this.user.createdCompany;
      localStorage.setItem(
        'toggleButtonCreatedCompany',
        JSON.stringify(this.user?.createdCompany)
      );
    }
  }

  onChangeCompanyDomain(ob: MatSlideToggleChange) {
    if (this.user != null) {
      this.user.companyDomain = !this.user.companyDomain;
      localStorage.setItem(
        'toggleButtonCompanyDomain',
        JSON.stringify(this.user?.companyDomain)
      );
    }
  }
  toggleCompanyNameValidator() {
    if (this.user?.employed) {
      this.editProfileForm.controls['companyName']?.setValidators([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(40),
      ]);
    }
    else{
      this.editProfileForm.controls['companyName']?.clearValidators();
    }
    this.editProfileForm.controls['companyName']?.updateValueAndValidity();
  }
}

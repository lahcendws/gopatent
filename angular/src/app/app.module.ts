import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { RouterModule } from "@angular/router";
import { routes } from './app-routing.module';
import { PatentBoxComponent } from './components/patent-box/patent-box.component';
import { PatentResultsPageComponent } from './components/patent-results-page/patent-results-page.component';
import { FooterComponent } from './components/footer/footer.component';
import { PatentSearchFormComponent } from './components/patent-search-form/patent-search-form.component';
import { EditProfileComponent } from "./components/profiles/edit-profile/edit-profile.component";
import { MyDomainsComponent } from './components/profiles/my-domains/my-domains.component';
import { MyPatentsComponent } from './components/profiles/my-patents/my-patents.component';
import { ProfilesComponent } from './components/profiles/profiles.component';
import { ProfileNavbarComponent } from './components/profiles/profile-navbar/profile-navbar.component';
import { LoginComponent } from './components/login/login.component';
import { AdminDashbordComponent } from './components/admin-dashbord/admin-dashbord.component';
import { authInterceptorProviders } from './helpers/auth.interceptor';
import { NgxPaginationModule } from 'ngx-pagination';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatIconModule} from "@angular/material/icon";
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import {SearchResults} from "./model/search-results";
import {DatePipe} from "@angular/common";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { PatentDetailsComponent } from './components/patent-details/patent-details.component';



@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    NavbarComponent,
    HomeComponent,
    FooterComponent,
    PatentBoxComponent,
    PatentResultsPageComponent,
    PatentSearchFormComponent,
    EditProfileComponent,
    MyDomainsComponent,
    MyPatentsComponent,
    ProfilesComponent,
    ProfileNavbarComponent,
    LoginComponent,
    AdminDashbordComponent,
    PatentDetailsComponent,
    ForgotPasswordComponent,
    UpdatePasswordComponent,

  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgxPaginationModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSlideToggleModule,
    MatProgressSpinnerModule

  ],
  providers: [authInterceptorProviders, SearchResults, DatePipe],
  bootstrap: [AppComponent]
})

export class AppModule {}

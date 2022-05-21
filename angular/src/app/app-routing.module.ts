import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {PatentResultsPageComponent} from "./components/patent-results-page/patent-results-page.component";
import {PatentSearchFormComponent} from "./components/patent-search-form/patent-search-form.component";
import {EditProfileComponent} from "./components/profiles/edit-profile/edit-profile.component";
import {MyDomainsComponent} from "./components/profiles/my-domains/my-domains.component";
import {MyPatentsComponent} from "./components/profiles/my-patents/my-patents.component";
import {ProfilesComponent} from "./components/profiles/profiles.component";
import { LoginComponent } from "./components/login/login.component";
import { AdminDashbordComponent } from './components/admin-dashbord/admin-dashbord.component';
import { IsLoggedInGuard } from './services/auth/isLoggedIn.guard';
import { AdminGuard } from './services/auth/admin.guard';
import { UpdatePasswordComponent} from './components/update-password/update-password.component';

const routes: Routes = [
  { path: 'inscription', component: RegistrationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'update-password', component: UpdatePasswordComponent },
  { path: 'admin/users', component: AdminDashbordComponent,canActivate: [AdminGuard] },
  { path: '', component: HomeComponent },
  { path: 'recherche', component: PatentSearchFormComponent,canActivate: [IsLoggedInGuard] },
  { path: 'resultats-recherche', component: PatentResultsPageComponent,canActivate: [IsLoggedInGuard]},

  { path: 'user/profile', component: ProfilesComponent,canActivate: [IsLoggedInGuard],  // this is the component with the <router-outlet> in the app template
    children: [
      {
        path: 'edit', // child route path
        component: EditProfileComponent,canActivate: [IsLoggedInGuard] // child route component that the router renders
      },
      {
        path: 'domains',
        component: MyDomainsComponent,canActivate: [IsLoggedInGuard]
      },
      {
        path: 'patents',
        component: MyPatentsComponent,canActivate: [IsLoggedInGuard]
      },
    ],
  },
  {
    path: '**',
    redirectTo: '/login',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export { routes };

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from './component/user-list/user-list.component';
import {TimelineComponent} from './component/timeline/timeline.component';
import {LoginComponent} from './component/login/login.component';
import {AuthenticationGuard} from './guards/authentication.guard';
import {ProfileComponent} from './component/profile/profile.component';
import {CreateKwetterComponent} from './component/create-kwetter/create-kwetter.component';
import {SearchKwetterComponent} from './component/search-kwetter/search-kwetter.component';

const routes: Routes = [
  // Login
  {path: 'login', component: LoginComponent},

  // Profile is home
  {path: '', component: ProfileComponent, canActivate: [AuthenticationGuard]},

  // Components
  {path: 'users', component: UserListComponent, canActivate: [AuthenticationGuard]},
  {path: 'timeline', component: TimelineComponent, canActivate: [AuthenticationGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthenticationGuard]},
  {path: 'create-kwetter', component: CreateKwetterComponent, canActivate: [AuthenticationGuard]},
  {path: 'search-kwetter', component: SearchKwetterComponent, canActivate: [AuthenticationGuard]},

  // Otherwise redirect to home
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

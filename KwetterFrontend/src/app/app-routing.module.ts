import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './component/user-list/user-list.component';
import { TimelineComponent } from './component/timeline/timeline.component';
import { LoginComponent } from './component/login/login.component';
import { AuthenticationGuard } from './service/authentication-guard.service';
import { ProfileComponent } from './component/profile/profile.component';
import { CreateKwetterComponent } from './component/create-kwetter/create-kwetter.component';
 
const routes: Routes = [
  { path: 'users', component: UserListComponent, canActivate: [AuthenticationGuard]},
  { path: 'timeline', component: TimelineComponent, canActivate: [AuthenticationGuard]},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthenticationGuard]},
  { path: 'create-kwetter', component: CreateKwetterComponent, canActivate: [AuthenticationGuard]},
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' }
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
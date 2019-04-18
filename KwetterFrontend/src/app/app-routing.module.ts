import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './component/user-list/user-list.component';
import { UserFormComponent } from './component/user-form/user-form.component';
import { LoginComponent } from './component/login-form/login-form.component';
import { AuthenticationGuard } from './service/authentication-guard.service';
 
const routes: Routes = [
  { path: 'users', component: UserListComponent, canActivate: [AuthenticationGuard]},
  { path: 'adduser', component: UserFormComponent, canActivate: [AuthenticationGuard]},
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' }
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/user-list/user-list.component';
import {LoginComponent} from './component/login/login.component';
import {UserService} from './service/user.service';
import {AuthenticationGuard} from './guards/authentication.guard';
import {TimelineComponent} from './component/timeline/timeline.component';
import {KwetterService} from './service/kwetter.service';
import {ProfileComponent} from './component/profile/profile.component';
import {CreateKwetterComponent} from './component/create-kwetter/create-kwetter.component';
import {SearchKwetterComponent} from './component/search-kwetter/search-kwetter.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    TimelineComponent,
    ProfileComponent,
    CreateKwetterComponent,
    SearchKwetterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [UserService, AuthenticationGuard, KwetterService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

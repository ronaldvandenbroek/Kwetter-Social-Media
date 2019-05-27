import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/user-list/user-list.component';
import {LoginComponent} from './component/login/login.component';
import {TimelineComponent} from './component/timeline/timeline.component';
import {ProfileComponent} from './component/profile/profile.component';
import {CreateKwetterComponent} from './component/create-kwetter/create-kwetter.component';
import {SearchKwetterComponent} from './component/search-kwetter/search-kwetter.component';
import {AlertComponent} from './component/alert/alert.component';
import {JwtInterceptor} from './interceptors/jwt.interceptor';
import {ErrorInterceptor} from './interceptors/error.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    TimelineComponent,
    ProfileComponent,
    CreateKwetterComponent,
    SearchKwetterComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
  ],
  bootstrap: [
    AppComponent,
  ]
})
export class AppModule {
}

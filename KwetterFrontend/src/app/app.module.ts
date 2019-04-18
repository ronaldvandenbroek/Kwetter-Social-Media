import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './component/user-list/user-list.component';
import { LoginComponent } from './component/login/login.component';
import { UserService } from './service/user.service';
import { AuthenticationGuard } from './service/authentication-guard.service';

import { ReactiveFormsModule } from "@angular/forms";
import { TimelineComponent } from './component/timeline/timeline.component';
import { TimelineService } from './service/timeline.service';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    TimelineComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [UserService, AuthenticationGuard, TimelineService],
  bootstrap: [AppComponent]
})
export class AppModule { }
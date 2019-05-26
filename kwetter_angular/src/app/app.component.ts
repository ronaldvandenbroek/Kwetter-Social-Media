import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from './service/authentication.service';
import {JwtToken} from './model/jwt-token';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentLogin: JwtToken;
  title = 'Kwetter';

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    this.authenticationService.currentLogin.subscribe(x => this.currentLogin = x);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { UrlTree } from '@angular/router';
 
@Injectable()
export class UserService {
  private headers: HttpHeaders;
 
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authenticationService.currentLoginValue.token
    })
  }
 
  public findAll(): Observable<User[]> {
    const url = 'http://localhost:8080/kwetter-1.0/api/token/secure/admin/get_all_users';
    return this.http.get<User[]>(url, { headers: this.headers })
  }

  public profile(): Observable<User> {
    const url = 'http://localhost:8080/kwetter-1.0/api/token/secure/user/profile/';
    return this.http.get<User>(url + this.authenticationService.currentLoginValue.user.id, { headers: this.headers })
  }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
 
@Injectable()
export class UserService {
  private headers: HttpHeaders;
  private baseURL: string;
 
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authenticationService.currentLoginValue.token
    })
    this.baseURL = 'http://localhost:8080/api/token/secure/'
  }
 
  public findAll(): Observable<User[]> {
    const url = this.baseURL + 'admin/get_all_users';
    return this.http.get<User[]>(url, { headers: this.headers })
  }

  public profile(): Observable<User> {
    const url = this.baseURL + 'user/profile/'  + this.authenticationService.currentLoginValue.user.id;
    return this.http.get<User>(url, { headers: this.headers })
  }

  public follow(followUser: User): Observable<void> {
    const url = this.baseURL + 'user/follow/'  + this.authenticationService.currentLoginValue.user.id;
    console.log(url);
    return this.http.post<void>(url, followUser, { headers: this.headers })
  }

  public unfollow(unfollowUser: User): Observable<void> {
    const url = this.baseURL + 'user/unfollow/'  + this.authenticationService.currentLoginValue.user.id;
    console.log(url);
    return this.http.post<void>(url, unfollowUser, { headers: this.headers })
  }
}
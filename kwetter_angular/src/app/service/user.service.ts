import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class UserService {
  private readonly headers: HttpHeaders;
  private readonly baseURL: string;

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.authenticationService.currentLoginValue.token
    });
    this.baseURL = 'http://localhost:8080/api/token/secure/';
  }

  public findAll(): Observable<User[]> {
    const url = this.baseURL + 'admin/get_all_users';
    return this.http.get<User[]>(url, {headers: this.headers});
  }

  public profile(): Observable<User> {
    const url = this.baseURL + 'user/profile/' + this.authenticationService.currentLoginValue.user.id;
    console.log(url);
    console.log(this.headers);
    return this.http.get<User>(url, {headers: this.headers});
  }

  public follow(followUser: User): Observable<User> {
    const url = this.baseURL + 'user/follow/' + this.authenticationService.currentLoginValue.user.id;
    console.log(url);
    console.log(this.headers);
    console.log(followUser);
    return this.http.post<User>(url, followUser, {headers: this.headers});
  }

  public unfollow(unfollowUser: User): Observable<User> {
    const url = this.baseURL + 'user/unfollow/' + this.authenticationService.currentLoginValue.user.id;
    console.log(url);
    console.log(this.headers);
    console.log(unfollowUser);
    return this.http.post<User>(url, unfollowUser, {headers: this.headers});
  }
}

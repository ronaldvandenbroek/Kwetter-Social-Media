import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  public findAll(): Observable<User[]> {
    const url = this.authenticationService.getHref('users');
    return this.http.get<User[]>(url, {headers: this.authenticationService.authenticationHeaders});
  }

  public profile(): Observable<User> {
    const url = this.authenticationService.getHref('profile');
    return this.http.get<User>(url, {headers: this.authenticationService.authenticationHeaders});
  }

  public follow(followUser: User): Observable<User> {
    const url = this.authenticationService.getHref('follow');
    return this.http.post<User>(url, followUser, {headers: this.authenticationService.authenticationHeaders});
  }

  public unfollow(unfollowUser: User): Observable<User> {
    const url = this.authenticationService.getHref('unfollow');
    return this.http.post<User>(url, unfollowUser, {headers: this.authenticationService.authenticationHeaders});
  }

  public following(): Observable<User[]> {
    const url = this.authenticationService.getHref('following');
    return this.http.get<User[]>(url, {headers: this.authenticationService.authenticationHeaders});
  }
}

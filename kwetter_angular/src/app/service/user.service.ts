import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserModel} from '../model/user.model';
import {Observable} from 'rxjs';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  public findAll(): Observable<UserModel[]> {
    const url = this.authenticationService.getHref('users');
    return this.http.get<UserModel[]>(url, {headers: this.authenticationService.authenticationHeaders});
  }

  public profile(): Observable<UserModel> {
    const url = this.authenticationService.getHref('profile');
    return this.http.get<UserModel>(url, {headers: this.authenticationService.authenticationHeaders});
  }

  public follow(followUser: UserModel): Observable<UserModel> {
    const url = this.authenticationService.getHref('follow');
    return this.http.post<UserModel>(url, followUser, {headers: this.authenticationService.authenticationHeaders});
  }

  public unfollow(unfollowUser: UserModel): Observable<UserModel> {
    const url = this.authenticationService.getHref('unfollow');
    return this.http.post<UserModel>(url, unfollowUser, {headers: this.authenticationService.authenticationHeaders});
  }

  public following(): Observable<UserModel[]> {
    const url = this.authenticationService.getHref('following');
    return this.http.get<UserModel[]>(url, {headers: this.authenticationService.authenticationHeaders});
  }
}

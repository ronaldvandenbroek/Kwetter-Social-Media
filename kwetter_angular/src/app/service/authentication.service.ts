import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

import {JwtTokenModel} from '../model/jwt-token.model';
import {UserModel} from '../model/user.model';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentLogin: Observable<JwtTokenModel>;
  private currentLoginSubject: BehaviorSubject<JwtTokenModel>;
  private readonly loginUrl: string;

  constructor(private http: HttpClient) {
    this.currentLoginSubject = new BehaviorSubject<JwtTokenModel>(JSON.parse(localStorage.getItem('currentLogin')));
    this.currentLogin = this.currentLoginSubject.asObservable();
    this.loginUrl = `http://localhost:8080/api/login`;
  }

  public get currentLoginValue(): JwtTokenModel {
    return this.currentLoginSubject.value;
  }

  public get currentLoginUser(): UserModel {
    return this.currentLoginSubject.value.user;
  }

  public getHref(rel: string): string {
    return this.currentLoginSubject.value.user.links.find(link => link.rel === rel).href;
  }

  login(email: string, password: string) {
    const body = {email, password};

    return this.http.post<JwtTokenModel>(this.loginUrl, body)
      .pipe(map(data => {
        // login successful if there's a jwt token in the response
        if (data && data.token && data.user) {
          console.log('Login successful');
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(data.user));
          this.currentLoginSubject.next(data);
        }
        return data.user;
      }));
  }

  logout() {
    localStorage.removeItem('currentLogin');
    this.currentLoginSubject.next(null);
  }
}

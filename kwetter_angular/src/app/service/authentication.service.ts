import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

import {JwtToken} from '../model/jwt-token';
import {User} from '../model/user';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentLogin: Observable<JwtToken>;
  private currentLoginSubject: BehaviorSubject<JwtToken>;
  private readonly loginUrl: string;

  constructor(private http: HttpClient) {
    this.currentLoginSubject = new BehaviorSubject<JwtToken>(JSON.parse(localStorage.getItem('currentLogin')));
    this.currentLogin = this.currentLoginSubject.asObservable();
    this.loginUrl = `http://localhost:8080/api/login`;
  }

  public get currentLoginValue(): JwtToken {
    return this.currentLoginSubject.value;
  }

  public get currentLoginUser(): User {
    return this.currentLoginSubject.value.user;
  }

  public getHref(rel: string): string {
    console.log(rel);
    console.log(this.currentLoginSubject.value.user.links);
    return this.currentLoginSubject.value.user.links.find(link => link.rel === rel).href;
  }

  login(email: string, password: string) {
    console.log('Login attempt');

    const body = {email, password};
    console.log(body);
    const response = this.http.post<JwtToken>(this.loginUrl, body);
    response.subscribe(data => {
      console.log(data.token);
      console.log(data.user.uuid);
      localStorage.setItem('currentLogin', JSON.stringify(data));
      this.currentLoginSubject.next(data);
    });
    return response;
  }

  logout() {
    localStorage.removeItem('currentLogin');
    this.currentLoginSubject.next(null);
  }
}

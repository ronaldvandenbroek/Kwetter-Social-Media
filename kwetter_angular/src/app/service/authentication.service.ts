import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

import {JwtTokenModel} from '../model/jwt-token.model';
import {UserModel} from '../model/user.model';

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

  public get authenticationHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.currentLoginSubject.value.token
    });
  }

  public getHref(rel: string): string {
    return this.currentLoginSubject.value.user.links.find(link => link.rel === rel).href;
  }

  login(email: string, password: string) {
    const body = {email, password};
    const response = this.http.post<JwtTokenModel>(this.loginUrl, body);
    response.subscribe(data => {
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

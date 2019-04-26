import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { JwtToken } from '../model/jwt-token';
import { User } from '../model/user';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentLogin: Observable<JwtToken>;
  private currentLoginSubject: BehaviorSubject<JwtToken>;

  constructor(private http: HttpClient) {
    this.currentLoginSubject = new BehaviorSubject<JwtToken>(JSON.parse(localStorage.getItem('currentLogin')));
    this.currentLogin = this.currentLoginSubject.asObservable();
  }

  public get currentLoginValue(): JwtToken {
    return this.currentLoginSubject.value;
  }

  public get currentLoginUser(): User {
    return this.currentLoginSubject.value.user;
  }

  login(email: string, password: string) {
    console.log("Login attempt");

    var body = {email, password};
    console.log(body);
    var response = this.http.post<JwtToken>(`http://localhost:8080/api/token/login`, body);
    response.subscribe(data => {
      console.log(data.token);
      console.log(data.user.id)
      localStorage.setItem('currentLogin', JSON.stringify(data));
      this.currentLoginSubject.next(data) })
    return response;
  }

  logout() {
    localStorage.removeItem('currentLogin');
    this.currentLoginSubject.next(null);
  }
}
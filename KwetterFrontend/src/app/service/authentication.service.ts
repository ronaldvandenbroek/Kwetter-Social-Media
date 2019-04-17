import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from "../model/user";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(email: string, password: string) {
    console.log("Login attempt");
    return this.http.post<any>(`http://localhost:8080/kwetter-1.0/api/token/login`, {email, password}).pipe(
        map((response: any) => {
            console.log(response);
            console.log(response.body);

            // // login successful if there's a jwt token in the response header
            // if (response.headers.get('Authorization')) {
            //   let user = response.body;
            //   if (user) {
            //     // store user details and jwt token in local storage to keep user logged in between page refreshes
            //     user.token = response.headers.get('Authorization');
            //     localStorage.setItem('currentUser', JSON.stringify(user));
            //     this.currentUserSubject.next(user);
            //   }
            //   console.log(this.currentUserSubject);
            // }

            //return token;
            return response;
          }
        ));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
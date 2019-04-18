import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
 
@Injectable()
export class UserService {
 
  private usersUrl: string;
 
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.usersUrl = 'http://localhost:8080/kwetter-1.0/api/token/secure/admin/get_all_users';
  }
 
  public findAll(): Observable<User[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authenticationService.currentLoginValue.token
    })
    return this.http.get<User[]>(this.usersUrl, { headers: headers })
  }
  
 
  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }
}
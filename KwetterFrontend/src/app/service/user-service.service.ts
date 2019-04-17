import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';
 
@Injectable()
export class UserService {
 
  private usersUrl: string;
  private headers: HttpHeaders;
 
  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/kwetter-1.0/api/token/secure/admin/get_all_users';
  }
 
  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl)
  }
  
 
  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }
}
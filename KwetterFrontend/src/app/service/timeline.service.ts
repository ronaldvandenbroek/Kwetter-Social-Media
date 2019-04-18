import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Kwetter } from '../model/kwetter';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
 
@Injectable()
export class TimelineService {

  private usersUrl: string;
 
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.usersUrl = 'http://localhost:8080/kwetter-1.0/api/token/secure/kwetter/timeline/';
  }
 
  public timeline(): Observable<Kwetter[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authenticationService.currentLoginValue.token
    })
    return this.http.get<Kwetter[]>(this.usersUrl + this.authenticationService.currentLoginValue.user.id, { headers: headers })
  }
}
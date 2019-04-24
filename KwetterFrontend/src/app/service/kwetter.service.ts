import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Kwetter } from '../model/kwetter';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
 
@Injectable()
export class KwetterService {
  private headers: HttpHeaders;
 
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authenticationService.currentLoginValue.token
    })
  }
 
  public timeline(): Observable<Kwetter[]> {
    return this.http.get<Kwetter[]>('http://localhost:8080/kwetter-1.0/api/token/secure/kwetter/timeline/' + this.authenticationService.currentLoginValue.user.id, { headers: this.headers })
  }

  createKwetter(text: string) {
    let kwetter = new Kwetter();
    kwetter.text = text;

    console.log(kwetter);

    var body = kwetter;
    console.log(body);
    var response = this.http.post<Kwetter>(`http://localhost:8080/kwetter-1.0/api/token/secure/kwetter/create/` + this.authenticationService.currentLoginValue.user.id, body, { headers: this.headers });
    response.subscribe(data => {
      console.log(data);
    })
    return response;
  }
}
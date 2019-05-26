import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Kwetter} from '../model/kwetter';
import {Observable} from 'rxjs';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class KwetterService {
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  public timeline(): Observable<Kwetter[]> {
    return this.http.get<Kwetter[]>(this.authenticationService.getHref('timeline'), {headers: this.authenticationService.authenticationHeaders});
  }

  createKwetter(text: string) {
    const kwetter = new Kwetter();
    kwetter.text = text;
    const response = this.http.post<Kwetter>(this.authenticationService.getHref('create'), kwetter, {headers: this.authenticationService.authenticationHeaders});
    response.subscribe(() => {
    });
    return response;
  }

  searchKwetter(text: string): Observable<Kwetter[]> {
    return this.http.post<Kwetter[]>(this.authenticationService.getHref('search'), text, {headers: this.authenticationService.authenticationHeaders});
  }
}

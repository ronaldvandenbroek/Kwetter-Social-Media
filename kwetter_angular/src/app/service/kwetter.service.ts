import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {KwetterModel} from '../model/kwetter.model';
import {Observable} from 'rxjs';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class KwetterService {
  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  public timeline(): Observable<KwetterModel[]> {
    return this.http.get<KwetterModel[]>(
      this.authenticationService.getHref('timeline'),
      {headers: this.authenticationService.authenticationHeaders});
  }

  createKwetter(text: string) {
    const kwetter = new KwetterModel();
    kwetter.text = text;
    const response = this.http.post<KwetterModel>(
      this.authenticationService.getHref('create'),
      kwetter,
      {headers: this.authenticationService.authenticationHeaders});
    response.subscribe(() => {
    });
    return response;
  }

  searchKwetter(text: string): Observable<KwetterModel[]> {
    return this.http.post<KwetterModel[]>(
      this.authenticationService.getHref('search'),
      text,
      {headers: this.authenticationService.authenticationHeaders});
  }
}

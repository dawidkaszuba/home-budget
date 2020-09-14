import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private http: HttpClient) { }

  public generateToken(request): Observable<any> {
    return this.http.post('http://localhost:8080/authenticate', request, { responseType: 'text' as 'json' });
  }

  public getBalance(token, userid): Observable<any> {
  let tokenStr = 'Bearer ' + token;
  const headers = new HttpHeaders().set("Authorization", tokenStr);
  return this.http.get('http://localhost:8080/users/' + userid + '/balance', { headers, responseType: 'text' as 'json' });
  }
}


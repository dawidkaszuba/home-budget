import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenResponse } from 'src/model/TokenResponse';
import { Balance } from 'src/model/Balance';
import { AuthRequest } from 'src/model/AuthRequest';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private http: HttpClient) { }

  public generateToken(request: AuthRequest): Observable<TokenResponse> {
    return this.http.post<TokenResponse>('http://localhost:8080/authenticate', request, { responseType: 'json' });
  }

  public getBalance(token, userid): Observable<Balance> {
    const tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<Balance>('http://localhost:8080/users/' + userid + '/balance', { headers, responseType: 'json' });
  }
}


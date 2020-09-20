import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenResponse } from 'src/model/TokenResponse';
import { Balance } from 'src/model/Balance';
import { AuthRequest } from 'src/model/AuthRequest';
import { ToolService } from '../tool.service';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private http: HttpClient, private toolService: ToolService) { }

  public generateToken(request: AuthRequest): Observable<TokenResponse> {
    return this.http.post<TokenResponse>('http://localhost:8080/authenticate', request, { responseType: 'json' });
  }

  public getBalance(): Observable<Balance> {
    return this.http.get<Balance>('http://localhost:8080/users/' +
    this.toolService.getUserIdFromLocalStorage() + '/balance');
  }
}



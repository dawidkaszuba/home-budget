import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Balance } from 'src/model/Balance';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BalanceService {

  private Balance: Balance;

  constructor(private httpService: HttpClient) { }

  public getBalance(): Observable<Balance> {
    const tokenStr = 'Bearer ' + localStorage.getItem('token');
    const userid = localStorage.getItem('userid');
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    const params = new HttpParams().set('from','2020-08-01').set('to', '2020-08-31');
    return this.httpService.get<Balance>('http://localhost:8080/users/' + userid + '/balance',
    { headers, responseType: 'json' , params});
  }
}

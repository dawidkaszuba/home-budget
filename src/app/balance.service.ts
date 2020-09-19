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
    const date = new Date();
    const firstDayOfCurrentMonth = new Date(date.getFullYear(), date.getMonth(), 1).toISOString().split('T')[0];
    const lastDayOfCurrentMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0).toISOString().split('T')[0];
    const params = new HttpParams().set('from', firstDayOfCurrentMonth).set('to', lastDayOfCurrentMonth);
    return this.httpService.get<Balance>('http://localhost:8080/users/' + userid + '/balance',
    { headers, responseType: 'json' , params});
  }
}

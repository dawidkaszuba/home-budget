import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expense } from 'src/model/Expense';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  constructor(private httpService: HttpClient ) { }

  public getAllIncomes(): Observable<Array<Expense>>{
    const userid = localStorage.getItem('userid');
    const tokenStr = 'Bearer ' + localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    const date = new Date();
    const firstDayOfCurrentMonth = new Date(date.getFullYear(), date.getMonth(), 2).toISOString().split('T')[0];
    const lastDayOfCurrentMonth = new Date(date.getFullYear(), date.getMonth() + 1, 2).toISOString().split('T')[0];
    const params = new HttpParams().set('from', firstDayOfCurrentMonth).set('to', lastDayOfCurrentMonth);
    return this.httpService.get<Array<Expense>>('http://localhost:8080/users/' + userid + '/expenditures',
      {  headers, responseType: 'json' , params});
  }
}

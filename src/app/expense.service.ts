import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expense } from 'src/model/Expense';
import { ToolService } from './tool.service';
import { PlannedCashFlow } from 'src/model/PlannedCashFlow';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  constructor(private httpService: HttpClient, private toolService: ToolService ) { }

  public getAllExpenses(): Observable<Array<Expense>>{
    const params = new HttpParams().set('from', this.toolService.getFirstDayOfCurrentMonth())
    .set('to', this.toolService.getLastDayOfCurrentMonth());
    return this.httpService.get<Array<Expense>>('http://localhost:8080/users/' +
    this.toolService.getUserIdFromLocalStorage() + '/expenditures',
      {params});
  }

  public saveExpense(request: Expense): void {
    this.httpService.post<Expense>('http://localhost:8080/users/'
    + this.toolService.getUserIdFromLocalStorage() + '/expenditures', request, { responseType: 'json' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Balance } from 'src/model/Balance';
import { Observable } from 'rxjs';
import { ToolService } from './tool.service';

@Injectable({
  providedIn: 'root'
})
export class BalanceService {

  constructor(private httpService: HttpClient, private toolService: ToolService) { }

  public getBalance(): Observable<Balance> {
    const params = new HttpParams().set('from', this.toolService.getFirstDayOfCurrentMonth())
    .set('to', this.toolService.getLastDayOfCurrentMonth());

    return this.httpService.get<Balance>('http://localhost:8080/users/' + this.toolService.getUserIdFromLocalStorage() +
    '/balance', {params});
  }
}

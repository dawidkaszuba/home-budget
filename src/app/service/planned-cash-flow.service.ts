import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToolService } from '../tool.service';
import { PlannedCashFlow } from 'src/model/PlannedCashFlow';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlannedCashFlowService {

  constructor(private httpService: HttpClient, private toolService: ToolService) { }

  public getAllPlannedCashFlow(): Observable<Array<PlannedCashFlow>> {
    const params = new HttpParams()
    .set('startDate', this.toolService.getFirstDayOfCurrentMonth())
    .set('endDate', this.toolService.getLastDayOfCurrentMonth());
    return this.httpService.get<Array<PlannedCashFlow>>('http://localhost:8080/users/' +
    this.toolService.getUserIdFromLocalStorage() + '/plannedCashFlows', {params});
  }
}

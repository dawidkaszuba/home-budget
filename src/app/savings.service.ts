import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToolService } from './tool.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SavingsService {

  constructor(private httpService: HttpClient, private toolService: ToolService) { }

  public getCurrentSavings(): Observable<Savings> {
    return this.httpService.get<Savings>('http://localhost:8080/users/' + this.toolService.getUserIdFromLocalStorage() +
    '/savings');
  }
}

import { Injectable } from '@angular/core';
import { tokenName } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class ToolService {

  constructor() { }

  public getUserIdFromLocalStorage(): string {
    return localStorage.getItem('userid');
  }

  public getFirstDayOfCurrentMonth(): string {
    const date = new Date();
    return new Date(date.getFullYear(), date.getMonth(), 2).toISOString().split('T')[0];
  }

  public getLastDayOfCurrentMonth(): string {
    const date = new Date();
    return new Date(date.getFullYear(), date.getMonth() + 1).toISOString().split('T')[0];
  }
}

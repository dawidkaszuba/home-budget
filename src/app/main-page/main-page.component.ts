import { Component, OnInit } from '@angular/core';
import { BalanceService } from '../balance.service';
import { ExpenseService } from '../expense.service';
import { Expense } from 'src/model/Expense';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(private balanceService: BalanceService, private expenseService: ExpenseService) { }

  currentBalance;
  expenses: Array<Expense>;

  ngOnInit(): void {
    this.showBalance();
    this.showAllExpensesForCurrentMonth();
  }

  public showBalance(): void{
    this.balanceService.getBalance().subscribe(balance => this.currentBalance = balance.value);
  }

  public showAllExpensesForCurrentMonth(): void{
    this.expenseService.getAllExpenses().subscribe(expenses => this.expenses = expenses);
  }
}

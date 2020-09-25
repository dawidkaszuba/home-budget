import { Component, OnInit } from '@angular/core';
import { BalanceService } from '../balance.service';
import { ExpenseService } from '../expense.service';
import { Expense } from 'src/model/Expense';
import { SavingsService } from '../savings.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(private balanceService: BalanceService,
              private expenseService: ExpenseService,
              private savingsService: SavingsService) { }

  currentBalance;
  currentSavings;
  expenses: Array<Expense>;
  amount: number;
  note: string;


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

  public showCurrentSavings(): void {
    this.savingsService.getCurrentSavings().subscribe(savings => this.currentSavings = savings.amount);
  }

}

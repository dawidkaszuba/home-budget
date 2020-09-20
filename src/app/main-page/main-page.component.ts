import { Component, OnInit } from '@angular/core';
import { BalanceService } from '../balance.service';
import { JwtClientService } from '../security/jwt-client.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(private balanceService: BalanceService) { }

   currentBalance;

  ngOnInit(): void {
    this.showBalance();
  }

  public showBalance(): void{
    this.balanceService.getBalance().subscribe(balance => this.currentBalance = balance.value);
  }
}

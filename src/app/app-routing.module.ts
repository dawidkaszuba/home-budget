import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainPageComponent } from './main-page/main-page.component';
import { AuthGuardService } from './auth-guard.service';
import { AddExpenseComponent } from './add-expense/add-expense.component';


const routes: Routes = [
  { path: '',   redirectTo: '/mainpage', pathMatch: 'full'},
  { path: 'mainpage', component: MainPageComponent,  canActivate: [AuthGuardService]},
  { path: 'login', component: LoginComponent },
  { path: 'addExpense', component: AddExpenseComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

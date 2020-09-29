import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AppComponent } from './app.component';
import { JwtClientService } from './jwt/jwt-client.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule } from '@angular/forms';
import { MainPageComponent } from './main-page/main-page.component';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { BalanceService } from './balance.service';
import { ExpenseService } from './expense.service';
import { ToolService } from './tool.service';
import { TokenInterceptor } from './TokenInterceptor';
import { SavingsService } from './savings.service';
<<<<<<< HEAD
import { MatSidenavModule } from '@angular/material/sidenav';
import { HeaderComponent } from './navigation/header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
=======
import { RootNavComponent } from './root-nav/root-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';


>>>>>>> 48007269ada5ec3ec8a4ed1a3771ba1567026777


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainPageComponent,
<<<<<<< HEAD
    HeaderComponent,
    SidenavListComponent
=======
    RootNavComponent
>>>>>>> 48007269ada5ec3ec8a4ed1a3771ba1567026777
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    FormsModule,
<<<<<<< HEAD
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatListModule

=======
    LayoutModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
>>>>>>> 48007269ada5ec3ec8a4ed1a3771ba1567026777
  ],
  providers: [JwtClientService, { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService, BalanceService, ExpenseService, ToolService, SavingsService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }

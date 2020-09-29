import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthService } from 'src/app/auth.service';
import { JwtClientService } from 'src/app/jwt/jwt-client.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.css']
})
export class SidenavListComponent implements OnInit {

  @Output()
  sidenavClose = new EventEmitter();

  constructor(public authService: AuthService, private router: Router ) { }

  ngOnInit(): void {
  }

  public onSidenavClose = () => {
    this.sidenavClose.emit();
  }

  public logOut(): void {
    this.authService.logOut();
  }

  public redirectToLoginPage(): void {
    this.router.navigate(['/login']);
  }

}

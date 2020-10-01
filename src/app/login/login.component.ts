import { Component, OnInit } from '@angular/core';
import { JwtClientService } from '../jwt/jwt-client.service';
import { AuthRequest } from 'src/model/AuthRequest';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private service: JwtClientService, private router: Router ) { }

  ngOnInit(): void {
  }

  public getAccessToken(): void {

    const authRequest: AuthRequest = new AuthRequest(this.username, this.password);
    const resp = this.service.generateToken(authRequest);
    resp.subscribe(data => localStorage.setItem('token', data.token));
    resp.subscribe(data => localStorage.setItem('userid', data.id));
    this.router.navigate(['/mainpage']);
  }
}

import { Component, OnInit } from '@angular/core';
import { JwtClientService } from '../security/jwt-client.service';
import { AuthRequest } from 'src/model/AuthRequest';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private service: JwtClientService) { }

  ngOnInit(): void {
  }

  public getAccessToken() {

    const authRequest: AuthRequest = new AuthRequest(this.username, this.password);
    const resp = this.service.generateToken(authRequest);
    resp.subscribe(data => localStorage.setItem('token', data.token));
  }
}

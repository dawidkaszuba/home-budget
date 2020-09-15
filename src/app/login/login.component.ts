import { Component, OnInit } from '@angular/core';
import { JwtClientService } from '../security/jwt-client.service';

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

    let authRequest  = {
      "username": this.username,
      "password": this.password
    };

    const resp = this.service.generateToken(authRequest);
    resp.subscribe(data => console.log("Token: " + data.token));
  }

}

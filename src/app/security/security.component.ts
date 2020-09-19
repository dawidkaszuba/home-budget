import { Component, OnInit } from '@angular/core';
import { JwtClientService } from './jwt-client.service';
import { TokenResponse } from 'src/model/TokenResponse';

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit {

  constructor(private service: JwtClientService) { }

  authRequest: any = {
    "username": "test",
    "password": "pass"
  };
  
   token: string;
   id: string;

  ngOnInit(): void {
    this.getAccessToken(this.authRequest);
  }

  public getAccessToken(authRequest) {
    const resp = this.service.generateToken(authRequest);
    resp.subscribe(data => this.token = data.token);
    resp.subscribe(data => this.id = data.id);
  }
}

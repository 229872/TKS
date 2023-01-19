import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Credentials} from "../model/Credentials";
import jwt_decode from 'jwt-decode';
import {Token} from "../model/Token";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public baseUrl = 'http://localhost:8080/rest/api'

  constructor(private httpClient: HttpClient) {
  }

  public login(credentials: Credentials): Observable<HttpResponse<Token>> {
    return this.httpClient.post<Token>(this.baseUrl + '/login', credentials,
      {observe: 'response'});
  }

  saveUserData(result: any) {
    const tokenInfo = this.getDecodedJwtToken(result.body.jwt);
    localStorage.setItem('email', result.body.email);
    localStorage.setItem('jwt', result.body.jwt);
    localStorage.setItem('role', tokenInfo.role);
  }

  getDecodedJwtToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }
}

import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Credentials, CredentialsChangePassword} from "../model/Credentials";
import jwt_decode from 'jwt-decode';
import {Token, TokenInfo} from "../model/Token";

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

  public changePassword(credentials: CredentialsChangePassword) {
    return this.httpClient.put<any>(this.baseUrl + '/changePassword',
      credentials, {headers: {
        'content-type': 'application/json'
        }, observe: 'response'});
  }

  public logout() {
    localStorage.removeItem('login');
    localStorage.removeItem('jwt');
    localStorage.removeItem('role');
  }

  public saveUserData(result: any) {
    try {
      const tokenInfo: TokenInfo = jwt_decode(result.body.jwt);
      localStorage.setItem('login', tokenInfo.sub);
      localStorage.setItem('jwt', result.body.jwt);
      localStorage.setItem('role', tokenInfo.userType);
    } catch (Error) {
      return;
    }
  }

  public isUserInRole(role: string) {
    return localStorage.getItem('role') == role;
  }

  public isUserLoggedIn() {
    return localStorage.getItem('role') != null;
  }

  public getLogin() {
    return localStorage.getItem('login');
  }

  public getToken() {
    return localStorage.getItem('jwt');
  }
}

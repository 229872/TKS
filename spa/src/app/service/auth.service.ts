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
    try {
      const tokenInfo = jwt_decode(result.body.jwt);
      // @ts-ignore
      localStorage.setItem('login', tokenInfo.sub);
      localStorage.setItem('jwt', result.body.jwt);
      // @ts-ignore
      localStorage.setItem('role', tokenInfo.role);
    } catch (Error) {
      return;
    }
  }
}

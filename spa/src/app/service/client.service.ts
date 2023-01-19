import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client} from "../model/User";

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  public baseUrl = 'http://localhost:8080/rest/api'

  constructor(private httpClient: HttpClient) {
  }

  public getByLogin(login: string): Observable<HttpResponse<Client>> {
    return this.httpClient.get<Client>(this.baseUrl + '/client/login/' + login,
      {observe: 'response'});
  }
}

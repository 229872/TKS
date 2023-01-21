import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client} from "../model/User";
import {Rent} from "../model/Rent";

@Injectable({
  providedIn: 'root'
})
export class RentService {
  public baseUrl = 'http://localhost:8080/rest/api'

  constructor(private httpClient: HttpClient) {
  }

  public createRent(rent: Rent): Observable<HttpResponse<Rent>> {
    return this.httpClient.post<Rent>(this.baseUrl + '/rents', rent,
      {observe: 'response'});
  }
}

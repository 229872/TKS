import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Equipment} from "../model/Equipment";

@Injectable({
  providedIn: 'root'
})
export class EquipmentService {
  public baseUrl = 'http://localhost:8080/rest/api'

  constructor(private httpClient: HttpClient) {
  }

  public getAll(): Observable<HttpResponse<Array<Equipment>>> {
    return this.httpClient.get<Array<Equipment>>(this.baseUrl + '/equipment',
      {observe: 'response'});
  }

}

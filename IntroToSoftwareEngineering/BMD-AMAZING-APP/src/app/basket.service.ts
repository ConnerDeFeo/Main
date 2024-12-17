import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable } from 'rxjs';
import { BasketResponse } from './basket-response';

@Injectable({
  providedIn: 'root'
})
export class BasketService {

  constructor(private http: HttpClient) { }

  /** GET basket by id. Will 404 if id not found */
  getBasket(id: number): Observable<BasketResponse> {
    const url = `${this.basketUrl}/${id}`;
    return this.http.get<BasketResponse>(url);
  }

  /** PUT: add a new need to the basket */
  addToBasket(uId: number, needId: number, quantity: number): Observable<Need> {
    const url  = `${this.basketUrl}/${uId}/${needId}/${quantity}`;
    return this.http.put<Need>(url, this.httpOptions);
  }

  /** DELETE: delete the need from the basket */
  removeFromBasket(uId: number, needId: number): Observable<Need[]> {
    const url = `${this.basketUrl}/${uId}/${needId}`;
    return this.http.delete<Need[]>(url, this.httpOptions);
  }

  checkout(id: number){
    return this.http.put<number>(this.basketUrl, id, this.httpOptions);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private basketUrl = 'http://localhost:8080/fundingBasket';  // URL to web api
}

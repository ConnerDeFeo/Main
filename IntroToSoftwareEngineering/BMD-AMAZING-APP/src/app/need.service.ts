import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NeedService {

  constructor(private http: HttpClient) { }

  /** GET Needs from the server */
  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsUrl);
  }

  /** GET Need by id. Will 404 if id not found */
  getNeed(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;
    return this.http.get<Need>(url);
  }

  /** PUT: update the need on the server */
  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.needsUrl, need, this.httpOptions);
  }

  /** POST: add a new need to the server */
  addNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.needsUrl, need, this.httpOptions);
  }

  /** DELETE: delete the need from the server */
  deleteNeed(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;

    return this.http.delete<Need>(url, this.httpOptions);
  }

  /* GET Needs whose name contains search term */
  searchNeeds(term: string): Observable<Need[]> {
    if (!term.trim()) {
      // if not search term, return full need array.
      return this.getNeeds();
    }
    return this.http.get<Need[]>(`${this.needsUrl}/?name=${term}`);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private needsUrl = 'http://localhost:8080/needs';  // URL to web api
}

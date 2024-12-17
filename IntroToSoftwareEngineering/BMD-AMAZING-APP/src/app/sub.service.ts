import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable } from 'rxjs';
import { SubscriptionResponse } from './subscription-response';

export interface Subscriptions {
    [key: string]: Need;
  }

@Injectable({
    providedIn: 'root'
})

export class SubService{

    constructor(private http: HttpClient) { }

    getUnprocessed(uId: number, needId: number): Observable<HttpResponse<any>> {
        const url = `${this.subUrl}/${uId}/${needId}`;
        return this.http.get<HttpResponse<any>>(url, { observe: 'response' });
    }

    addUnprocessedSubscription(uId: number, needId: number, quantity: number): Observable<Need>{
        const url  = `${this.subUrl}/${uId}/${needId}/${quantity}`;
        return this.http.put<Need>(url,this.httpOptions);
    }

    deleteUnprocessedSubscription(uId: number, needId: number): Observable<Need>{
        const url  = `${this.subUrl}/${uId}/unprocessed/${needId}`;
        return this.http.delete<Need>(url,this.httpOptions);
    }

    addSubscriptions(uId: number): Observable<Need>{
        const url  = `${this.subUrl}/${uId}/${this.getCurrentDate()}`;
        return this.http.put<Need>(url,this.httpOptions);
    }

    deleteSubscription(uId: number, needId: number): Observable<Need>{
        const url  = `${this.subUrl}/${uId}/processed/${needId}`;
        return this.http.delete<Need>(url,this.httpOptions);
    }

    getSubscription(uId: number): Observable<SubscriptionResponse>{
        const url  = `${this.subUrl}/${uId}`;
        return this.http.get<SubscriptionResponse>(url,this.httpOptions);
    }

    private getCurrentDate(): string {
        const today = new Date();
        const month = (today.getMonth() + 1).toString().padStart(2, '0');
        const day = today.getDate().toString().padStart(2, '0');
        const year = today.getFullYear();
        return `${month}-${day}-${year}`;
    }

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'text/plain' })
      };

    private subUrl = 'http://localhost:8080/subscriptions';
}
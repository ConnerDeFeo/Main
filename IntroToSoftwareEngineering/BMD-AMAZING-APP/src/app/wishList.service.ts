import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable } from 'rxjs';
import { WishListResponse } from './wishlistResponse';

@Injectable({
    providedIn: 'root'
})
export class WishListService{
    constructor(private http: HttpClient) {}
    private wishlistUrl = 'http://localhost:8080/wishList';
    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    getWishlist(id: number): Observable<WishListResponse>{
        const url = `${this.wishlistUrl}/${id}`;
        return this.http.get<WishListResponse>(url);
    }

    addToWishlist(uId: number, needId: number, quantity: number): Observable<Need> {
        const url  = `${this.wishlistUrl}/${uId}/${needId}/${quantity}`;
        return this.http.put<Need>(url, this.httpOptions);
      }
    
      /** DELETE: delete the need from the basket */
    removeFromWishList(uId: number, needId: number): Observable<Need[]> {
        const url = `${this.wishlistUrl}/${uId}/${needId}`;
        return this.http.delete<Need[]>(url, this.httpOptions);
    }
}
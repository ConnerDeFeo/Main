import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { catchError, map, Observable, of} from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  
  private role: 'admin' | 'user' | 'unassigned' = 'unassigned';
  private defaultUser: User = {id: -1, username: ''};
  private currentUser: User = this.defaultUser;

  constructor(private http: HttpClient){}

  signup(username: string, confirmUsername: string): Observable<User>{
    if(username !== confirmUsername){
      return of(this.defaultUser);
    }
    const newUser: User = {id:-2, username: username};
    return this.http.post<User>(this.authUrl, newUser, { observe: 'response' }).pipe(
      map(response => {return response.body ? response.body: this.defaultUser}),
      catchError(() => of(this.defaultUser))
    );
  }

  signin(username: string): Observable<User>{
    const url = `${this.authUrl}/signIn`;
    return this.http.put<User>(url, username,{ observe: 'response' }).pipe(
      map(response => {return response.body ? response.body: this.defaultUser}),
      catchError(() => of(this.defaultUser))
    );
  }

  handleSigninResponse(response: User){
    this.currentUser = response;
    if(response.id === 0){
      this.role ='admin'
    }
    else{
      this.role = 'user'
    }
  }

  signout(id: number): Observable<User>{
    const url = `${this.authUrl}/signOut`;
    this.role = 'unassigned';
    this.currentUser = this.defaultUser;
    return this.http.put<User>(url, id);
  }

  getCurrentUser(): User{
    return this.currentUser;
  }

  getRole(): 'admin' | 'user' | 'unassigned'{
    return this.role;
  }

  private authUrl = 'http://localhost:8080/users';
}

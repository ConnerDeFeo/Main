import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css'
})
export class SigninComponent {

  username: string = '';
  errorMessage: string = '';

  constructor(private authenticationService: AuthenticationService, private router: Router){}

  signin(){
    this.authenticationService.signin(this.username).subscribe(
      response => {
        if (response.id !== -1) {
          this.authenticationService.handleSigninResponse(response);
          this.router.navigate(['/dashboard']);
        }
        else {
          this.errorMessage = 'Invalid credentials';
        }
      }
    );
  }
}

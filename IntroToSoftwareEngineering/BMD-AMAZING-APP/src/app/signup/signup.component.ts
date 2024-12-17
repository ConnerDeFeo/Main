import { Component } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  
  username: string = '';
  confirmUsername: string = '';
  errorMessage: string = '';

  constructor(private authenticationService: AuthenticationService, private router: Router){}

  signup(){
    this.authenticationService.signup(this.username, this.confirmUsername).subscribe(
      response => {
        if (response.id !== -1) {
          this.authenticationService.signin(response.username).subscribe();
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

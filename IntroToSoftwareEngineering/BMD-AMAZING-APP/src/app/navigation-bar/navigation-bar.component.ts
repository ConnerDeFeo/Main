import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { Subscription } from 'rxjs';

interface NavigationLink{
  label: string;
  path?: string;
  roles: ('admin' | 'user' | 'unassigned')[];
  action?: () => void;
}

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

  currentPath: string;
  routerSubscription: Subscription;


  constructor(private router: Router, private authenticationService: AuthenticationService){
    this.currentPath = '/dashboard';
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentPath = event.urlAfterRedirects;
      }
    });
  }

  // Ensures that current path changes whenever routing ends
  // so that the navigation bar isn't redundant.
  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentPath = event.url;
        // This is a somewhat sloppy fix to a bug wherein if initializing with '/'
        // Dashboard link would still appear despite rerouting to /dashboard
        if(this.currentPath === '/'){
          this.currentPath = '/dashboard'
        }
      }
    });
  }

  // Includes all paths that can be on Navigation Bar
  links: NavigationLink[] = [
    {label: 'Home', path: '/dashboard', roles: ['unassigned', 'user', 'admin']},
    {label: 'Profile', path: '/profile', roles: ['user']},
    {label: 'Needs', path: '/needs', roles: ['unassigned', 'admin', 'user']},
    {label: 'Basket', path: '/basket', roles: ['user']},
    {label: 'WishList', path: '/wishlist', roles: ['user']},
    {label: 'Sign In', path: '/signin', roles: ['unassigned']},
    {label: 'Sign Out', roles: ['user', 'admin'], action: () => this.signout()}
  ]

  get filteredLinks() {
    const userRole = this.authenticationService.getRole();
    return this.links.filter(link => {
      return link.roles.includes(userRole);
    });
  }

  signout() {
    this.authenticationService.signout(this.authenticationService.getCurrentUser().id).subscribe(
        response => {
          console.log("Sign out successful", response);
          this.router.navigate(['/dashboard']);
        },
        error => {
          console.error("Sign out failed", error);
        }
    );
  }
}

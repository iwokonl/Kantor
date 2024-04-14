import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AxiosService } from './axios.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private axiosService: AxiosService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const isLoggedIn = this.axiosService.isLogged(); //... check if the user is logged in
    if (isLoggedIn) {
      return true;
    } else {
      this.router.navigate(['/login']); // if not logged in, redirect to /login
      return false;
    }
  }
}

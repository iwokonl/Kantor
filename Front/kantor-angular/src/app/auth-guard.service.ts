import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {AxiosService} from './axios.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private axiosService: AxiosService, private router: Router) {
  }

  //Funkcja do routowania na stronę główną jeśli użytkownik jest zalogowany
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const isLoggedIn = this.axiosService.getAuthTocken() !== null;
    if (isLoggedIn) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }

}

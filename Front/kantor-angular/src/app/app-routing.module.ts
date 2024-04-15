import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponentComponent} from "./home-component/home-component.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {RegisterFormComponent} from "./register-form/register-form.component";
import { CurrencyDetailComponent } from "./currency-detail/currency-detail.component";
import { CurrencyAccountComponent } from './currency-account/currency-account.component';
import { AuthGuardService } from './auth-guard.service';
import {UserInfoComponent} from "./user-info/user-info.component";


const routes: Routes = [
  {path: '', component: HomeComponentComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'register', component: RegisterFormComponent},
  { path: 'currency-account', component: CurrencyAccountComponent, canActivate: [AuthGuardService] },
  { path: 'userInfo', component: UserInfoComponent},


  { path: ':code', component: CurrencyDetailComponent},

];
@NgModule({

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import {HomeComponentComponent} from "./home-component/home-component.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {RegisterFormComponent} from "./register-form/register-form.component";
import { CurrencyDetailComponent } from "./currency-detail/currency-detail.component";
import { CurrencyAccountComponent } from './currency-account/currency-account.component';
import { SettingsComponent} from "./settings/settings.component";
import {WelcomeContentComponent} from "./welcome-content/welcome-content.component";
import { ExchangeRatesComponent } from './exchange-rates/exchange-rates.component';
import { AuthGuard } from './auth-guard.service';

const routes: Routes = [
  // {path: '', component: HomeComponentComponent},
  {path: '', component: WelcomeContentComponent},
  {path: 'login', component: LoginFormComponent, canActivate: [AuthGuard]},
  {path: 'register', component: RegisterFormComponent, canActivate: [AuthGuard]},
  { path: 'currency-account', component: CurrencyAccountComponent},
  { path: 'currency/:code', component: CurrencyDetailComponent },
  { path: 'settings', component: SettingsComponent},
  { path: 'exchange-rates', component: ExchangeRatesComponent}
];
@NgModule({

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

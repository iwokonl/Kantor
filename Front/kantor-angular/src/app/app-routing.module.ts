import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import {HomeComponentComponent} from "./home-component/home-component.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {RegisterFormComponent} from "./register-form/register-form.component";
import { CurrencyDetailComponent } from "./currency-detail/currency-detail.component";
import { CurrencyAccountComponent } from './currency-account/currency-account.component';
import { SettingsComponent} from "./settings/settings.component";
import {WelcomeContentComponent} from "./welcome-content/welcome-content.component";


const routes: Routes = [
  // {path: '', component: HomeComponentComponent},
  {path: '', component: WelcomeContentComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'register', component: RegisterFormComponent},
  { path: 'currency-account', component: CurrencyAccountComponent},
  { path: 'currency/:code', component: CurrencyDetailComponent },
  { path: 'settings', component: SettingsComponent},
];
@NgModule({

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

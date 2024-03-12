import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponentComponent} from "./home-component/home-component.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {RegisterFormComponent} from "./register-form/register-form.component";


const routes: Routes = [
  {path: '', component: HomeComponentComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'register', component: RegisterFormComponent}
];
@NgModule({

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

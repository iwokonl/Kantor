import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthContentComponent } from './auth-content/auth-content.component';
import {FormsModule} from "@angular/forms";

import { WelcomeContentComponent } from './welcome-content/welcome-content.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { ContentsComponent } from './contents/contents.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { ButtonsComponent } from './buttons/buttons.component';
import { HomeComponentComponent } from './home-component/home-component.component';
import { HeaderComponent } from './header/header.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { SearchBarComponent } from './search-bar/search-bar.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { HttpClientModule } from '@angular/common/http';
import { CurrencyAccountComponent } from './currency-account/currency-account.component';
import { CurrencyDetailComponent } from './currency-detail/currency-detail.component';
import { UserinfoComponent } from './userinfo/userinfo.component';



@NgModule({
  declarations: [
    AppComponent,
    AuthContentComponent,

    WelcomeContentComponent,
    LoginFormComponent,
    ContentsComponent,
    RegisterFormComponent,
    ButtonsComponent,
    HomeComponentComponent,
    HeaderComponent,
    SearchBarComponent,
    SearchResultsComponent,
    CurrencyAccountComponent,
    CurrencyDetailComponent,
    UserinfoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

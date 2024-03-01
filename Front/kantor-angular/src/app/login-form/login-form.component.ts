import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {
  @Output() onSubmitLoginEvent = new EventEmitter();
  login: string = '';
  password: string = '';

  onSubmitLogin() {
    this.onSubmitLoginEvent.emit({"login": this.login, "password": this.password});
  }
}

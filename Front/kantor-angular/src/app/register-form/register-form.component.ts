import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss'
})
export class RegisterFormComponent {
@Output() onSubmitRegisterEvent = new EventEmitter();
firstName: string = '';
lastName: string = '';
email: string = '';
login: string = '';
password: string = '';

onSubmitRegister() {
  this.onSubmitRegisterEvent.emit({"firstName": this.firstName, "lastName": this.lastName, "email": this.email, "login": this.login, "password": this.password});
}

}

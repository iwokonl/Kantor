export class User{
  private _id: number;
  private _role: string;
  private _username: string;
  private _firstName: string;
  private _lastName: string;
  private _email: string;
  private _token: string;


  constructor(id: number, role: string, username: string, firstName: string, lastName: string, email: string, token: string) {
    this._id = id;
    this._role = role;
    this._username = username;
    this._firstName = firstName;
    this._lastName = lastName;
    this._email = email;
    this._token = token;
  }

  get id(): number {
    return this._id;
  }

  get role(): string {
    return this._role;
  }

  get username(): string {
    return this._username;
  }

  get firstName(): string {
    return this._firstName;
  }

  get lastName(): string {
    return this._lastName;
  }

  get email(): string {
    return this._email;
  }

  get token(): string {
    return this._token;
  }
}

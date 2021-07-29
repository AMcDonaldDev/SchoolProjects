export class Credentials {
  public username: string = "";
  public password: string = "";
  public email: string = "";

  public passwordCheck: string = "";

  // For Updating Profile
  public changePassword: string = "";
  public changePasswordCheck: string = "";

  constructor() { }

  public fromJSON(json: any): Credentials {
    this.username = json.username;
    this.password = json.password;
    this.email = json.email;
    return this;
  }

  public finalize(): Credentials {
    let copy = new Credentials().fromJSON(this);
    return copy;
  }
}

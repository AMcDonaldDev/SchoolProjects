import { UserProfile } from './user-profile';
import { Credentials } from './credentials';

export class BasicUser {
  public credentials: Credentials;
  public userProfile: UserProfile;

  constructor() {
    this.credentials = new Credentials();
    this.userProfile = new UserProfile();
  }

  public fromJSON(json: any): BasicUser {
    this.credentials = new Credentials().fromJSON(json.credentials);
    this.userProfile = new UserProfile().fromJSON(json.userProfile);

    return this;
  }

  public finalize(): BasicUser {
    return this;
  }
}

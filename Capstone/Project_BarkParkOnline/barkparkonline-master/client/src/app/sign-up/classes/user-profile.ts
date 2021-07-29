import { Visit } from 'src/app/calendar/visit';
import { DogProfile } from './dog-profile';
import { PhoneNumber } from './phone-number';

export class UserProfile {
  public dogs = new Array<DogProfile>();
  public visits = new Array<Visit>();
  public visitIds = new Array<string>();
  public firstName: string = "";
  public lastName: string = "";
  public phoneNumber: string = "";
  public profilePhotoUrl: string = "";

  public fullName: string = "";

  public phoneNumberModel: PhoneNumber;

  constructor() {
    this.phoneNumberModel = new PhoneNumber();
  }

  public fromJSON(json: any): UserProfile {
    for (let dog of json.dogs) {
      this.dogs.push(new DogProfile().fromJSON(dog));
    }
    for (let visitId of json.visitIds) {
      this.visitIds.push(visitId);
    }
    this.firstName = json.firstName;
    this.lastName = json.lastName;
    this.fullName = this.firstName + " " + this.lastName;
    this.phoneNumber = json.phoneNumber;
    this.phoneNumberModel = new PhoneNumber().fromString(json.phoneNumber);
    this.profilePhotoUrl = json.profilePhotoUrl;
    return this;
  }

  public finalize(): UserProfile {
    if (this.profilePhotoUrl.length == 0) {
      this.profilePhotoUrl = null;
    }
    this.phoneNumber = this.phoneNumberModel.format();
    if (this.phoneNumber.length != 12) {
      this.phoneNumber = null;
    }
    let copy = new UserProfile().fromJSON(this);
    return copy;
  }
}

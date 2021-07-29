import { Visit } from './visit';

export class BarkPark {
  public _id: string = "";
  public name: string = "";
  public streetNumber: string = "";
  public streetName: string = "";
  public cityName: string = "";
  public stateName: string = "";
  public zipCode: number = 0;
  public visits: Array<Visit>;

  constructor() {
    this.visits = new Array<Visit>();
  }

  public fromJSON(json: any): BarkPark {
    this._id = json.id;
    this.name = json.name;
    this.streetNumber = json.streetNumber;
    this.streetName = json.streetName;
    this.cityName = json.cityName;
    this.stateName = json.stateName;
    this.zipCode = json.zipCode;
    for (let visit of json.visits) {
      this.visits.push(new Visit().fromJSON(visit));
    }
    return this;
  }
}
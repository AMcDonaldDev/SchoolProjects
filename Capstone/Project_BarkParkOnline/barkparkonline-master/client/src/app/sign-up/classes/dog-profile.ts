export class DogProfile {
  public dogName: string = "";
  public dogBreed: string = "";
  public dogDob: any = "";
  public dogGender: string = "MALE";

  constructor() { }

  public fromJSON(json: any): DogProfile {
    this.dogName = json.dogName;
    this.dogBreed = json.dogBreed;
    this.dogDob = json.dogDob;
    this.dogGender = json.dogGender;
    return this;
  }

  public finalize(): DogProfile {
    if (this.dogDob instanceof Date) {
      let date = <Date>this.dogDob;
      let year = date.getFullYear();
      let month = "" + (date.getMonth() + 1);
      let day = "" + (date.getDate() + 1);
      if (day.toString().length == 1) { day = "0" + day;}
      if (month.toString().length == 1) { month = "0" + month;}
      this.dogDob = year + "-" + month + "-" + day;
    }
    this.dogGender = this.dogGender.toUpperCase();
    let dog = new DogProfile().fromJSON(this);
    return dog;
  }

  public validate() {
    if (this.dogName.length == 0) {
      throw new Error("Dog's Name is Empty");
    }
    if (this.dogBreed.length == 0) {
      throw new Error("Dog's Breed is Empty");
    }
    if (this.dogDob.length == 0) {
      throw new Error("Dog is Missing Data of Birth");
    }
  }
}

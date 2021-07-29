export class Visit {
  visitId: string = "";
  visitorFullName: string = "";
  date: any = new Date();
  time: string = "";
  visitorDogNames: Array<string> = new Array<string>();
  parkId: string = "";

  constructor() {}

  public fromJSON(json: any): Visit {
    this.visitId = json.visitId;
    this.visitorFullName = json.visitorFullName;
    this.date = this.parseDate(json.date, json.time);
    this.time = json.time;
    for (let dog of json.visitorDogNames) {
      this.visitorDogNames.push(dog);
    }
    this.parkId = json.parkId;
    return this;
  }

  private parseDate(rawDate: string, rawTime: string) : Date {
    let parsed = new Date();
    let dateSplit = rawDate.split("-");
    let timeSplit = rawTime.split(":");
    parsed.setFullYear(+dateSplit[0]);
    parsed.setMonth((+dateSplit[1])-1);
    parsed.setDate(+dateSplit[2]);
    parsed.setHours(+timeSplit[0]);
    parsed.setMinutes(+timeSplit[1]);
    return parsed;
  }

  public getTimeHour(): number {
    return Number.parseInt(this.time.split(":")[0]);
  }

  public getTimeMinutes(): number {
    return Number.parseInt(this.time.split(":")[1]);
  }

  public finalize(): Visit {
    if (this.date instanceof Date) {
      let date = <Date>this.date;
      let year = date.getFullYear();
      let month = "" + (date.getMonth() + 1);
      let day = "" + (date.getDate());
      if (day.toString().length == 1) { day = "0" + day; }
      if (month.toString().length == 1) { month = "0" + month; }
      this.date = year + "-" + month + "-" + day;
    }
    let hour = "" + (this.time.split(":")[0]);
    let minutes = "" + (this.time.split(":")[1]);
    if (hour.length == 1) { hour = "0" + hour; }
    if (minutes.length == 1) { minutes = "0" + minutes; }
    this.time = hour + ":" + minutes;
    return this;
  }
}

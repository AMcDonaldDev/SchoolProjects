export class PhoneNumber {
  public area: string = "";
  public exchange: string = "";
  public subscriber: string = "";

  constructor() { }

  public fromString(str: string): PhoneNumber {
    if (!str) return this;
    if (str.includes("-")) {
      let sections = str.split("-")
      this.area = sections[0];
      this.exchange = sections[1];
      this.subscriber = sections[2];
      return this;
    }
    this.area = str.slice(0, 2);
    this.exchange = str.slice(3, 5);
    this.subscriber = str.slice(6, 9);
    return this;
  }

  public format(): string {
    return this.area + "-" +
      this.exchange + "-" +
      this.subscriber;
  }
}

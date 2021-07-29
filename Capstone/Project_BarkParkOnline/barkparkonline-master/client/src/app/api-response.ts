export class ApiResponse {
  public statusCode: number = 200;
  public message: any = "";

  constructor() {}

  public fromJSON(json: any): ApiResponse {
    this.statusCode = json.statusCode;
    this.message = json.message;
    return this;
  }
}

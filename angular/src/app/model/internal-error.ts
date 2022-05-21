export class InternalError {

  private _internalError: boolean = false;

  get internalError(): boolean {
    return this._internalError;
  }

  set internalError(value: boolean) {
    this._internalError = value;
  }

  constructor() {}

}

export class PatentInfo {

    private _patentNumber?: string;
    private _title?: string;
    private _releaseDate?: Date;
    private _applicantName?: string;
    private _inventorsArrayList?: string[];
    private _ipc?: String;
    private _domains?: string[];
    private _espaceNetUrl?: string;
    private _patentAbstract?: string;
    private _figure?: string;

  get patentNumber(): string {
    return <string>this._patentNumber;
  }

  set patentNumber(value: string) {
    this._patentNumber = value;
  }

  get title(): string {
    return <string>this._title;
  }

  set title(value: string) {
    this._title = value;
  }

  get releaseDate(): Date {
    return <Date>this._releaseDate;
  }

  set releaseDate(value: Date) {
    this._releaseDate = value;
  }

  get applicantName(): string {
    return <string>this._applicantName;
  }

  set applicantName(value: string) {
    this._applicantName = value;
  }

  get inventorsArrayList(): string[] {
    return <string[]>this._inventorsArrayList;
  }

  set inventorsArrayList(value: string[]) {
    this._inventorsArrayList = value;
  }

  get ipc(): String {
    return <String>this._ipc;
  }

  set ipc(value: String) {
    this._ipc = value;
  }

  get domains(): string[] {
    return <string[]>this._domains;
  }

  set domains(value: string[]) {
    this._domains = value;
  }

  get espaceNetUrl(): string {
    return <string>this._espaceNetUrl;
  }

  set espaceNetUrl(value: string) {
    this._espaceNetUrl = value;
  }

  get patentAbstract(): string {
    return <string>this._patentAbstract;
  }

  set patentAbstract(value: string) {
    this._patentAbstract = value;
  }

  get figure(): string {
    return <string>this._figure;
  }

  set figure(value: string) {
    this._figure = value;
  }
}

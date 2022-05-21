export class PatentModel {
  private _patentNumber: string;
  private _title: string;
  private _favorite: boolean;
  private _releaseDate: Date;
  private _applicantName: string;
  private _inventorsArrayList: string[];
  private _familyId: string;
  private _ipc: string;
  private _domains: string[];
  private _bigDomains: string[];
  private _espaceNetUrl: string;
  private _patentAbstract: string;
  private _figure: string;


  constructor(patentNumber: string, title: string, favorite: boolean, releaseDate: Date, applicantName: string, inventorsArrayList: string[], familyId: string, ipc: string, domains: string[], bigDomains: string[], espaceNetUrl: string, patentAbstract: string, figure: string) {
    this._patentNumber = patentNumber;
    this._title = title;
    this._favorite = favorite;
    this._releaseDate = releaseDate;
    this._applicantName = applicantName;
    this._inventorsArrayList = inventorsArrayList;
    this._familyId = familyId;
    this._ipc = ipc;
    this._domains = domains;
    this._bigDomains = bigDomains;
    this._espaceNetUrl = espaceNetUrl;
    this._patentAbstract = patentAbstract;
    this._figure = figure;
  }


    get favorite(): boolean {
      return this._favorite;
    }

    set favorite(value: boolean) {
      this._favorite = value;
    }

  get patentNumber(): string {
    return this._patentNumber;
  }

  set patentNumber(value: string) {
    this._patentNumber = value;
  }

  get title(): string {
    return this._title;
  }

  set title(value: string) {
    this._title = value;
  }


  get releaseDate(): Date {
    return this._releaseDate;
  }

  set releaseDate(value: Date) {
    this._releaseDate = value;
  }

  get applicantName(): string {
    return this._applicantName;
  }

  set applicantName(value: string) {
    this._applicantName = value;
  }

  get inventorsArrayList(): string[] {
    return this._inventorsArrayList;
  }

  set inventorsArrayList(value: string[]) {
    this._inventorsArrayList = value;
  }

  get familyId(): string {
    return this._familyId;
  }

  set familyId(value: string) {
    this._familyId = value;
  }

  get ipc(): string {
    return this._ipc;
  }

  set ipc(value: string) {
    this._ipc = value;
  }

  get domains(): string[] {
    return this._domains;
  }

  set domains(value: string[]) {
    this._domains = value;
  }

  get bigDomains(): string[] {
    return this._bigDomains;
  }

  set bigDomains(value: string[]) {
    this._bigDomains = value;
  }

  get espaceNetUrl(): string {
    return this._espaceNetUrl;
  }

  set espaceNetUrl(value: string) {
    this._espaceNetUrl = value;
  }

  get patentAbstract(): string {
    return this._patentAbstract;
  }

  set patentAbstract(value: string) {
    this._patentAbstract = value;
  }

  get figure(): string {
    return this._figure;
  }

  set figure(value: string) {
    this._figure = value;
  }
}


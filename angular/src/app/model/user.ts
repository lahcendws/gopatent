export class User {
  private _id?: number;
  private _username?: string;
  private _firstname?: string;
  private _lastname?: string;
  private _email?: string;
  private _password?: string;
  private _roles?: string[];
  private _employed?: boolean = false;
  private _createdCompany = false;
  private _companyDomain = false;
  private _companyName?: string;

  constructor() {
  }

  get id(): number {
    return <number>this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get username(): any {
    return this._username;
  }

  set username(value: any) {
    this._username = value;
  }

  get firstname(): string {
    return <string>this._firstname;
  }

  set firstname(value: string) {
    this._firstname = value;
  }

  get lastname(): string {
    return <string>this._lastname;
  }

  set lastname(value: string) {
    this._lastname = value;
  }

  get email(): string {
    return <string>this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get password(): string {
    return <string>this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get roles(): string[] {
    return <string[]>this._roles;
  }

  set roles(value: string[]) {
    this._roles = value;
  }

  get employed(): boolean {
    return <boolean>this._employed;
  }

  set employed(value: boolean) {
    this._employed = value;
  }

  get createdCompany(): boolean {
    return this._createdCompany;
  }

  set createdCompany(value: boolean) {
    this._createdCompany = value;
  }

  get companyDomain(): boolean {
    return this._companyDomain;
  }

  set companyDomain(value: boolean) {
    this._companyDomain = value;
  }

  get companyName(): string {
    return <string>this._companyName;
  }

  set companyName(value: string) {
    this._companyName = value;
  }
}

import {PatentModel} from "./patent-model";

export class SearchResults {
  private _results: PatentModel[];


  constructor() {
    this._results = [];
  }

  getResults(): PatentModel[] {
    return this._results;
  }


  setResults(value: PatentModel[]) {
    this._results = value;
  }
}

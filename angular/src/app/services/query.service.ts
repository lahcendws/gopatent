import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchResults} from "../model/search-results";
import {DatePipe} from "@angular/common";
import {PatentModel} from "../model/patent-model";
import {environment} from "../../environments/environment";
import {InternalError} from "../model/internal-error";

@Injectable({
  providedIn: 'root'
})

/**
 * Service to query the search endpoint
 */
export class QueryService {

  searchResults: SearchResults = new SearchResults();
  PATENT_SEARCH_API: string = environment.PATENT_SEARCH_API;
  currentQuery: String | undefined = "";
  internalError: InternalError = new InternalError()


  constructor(private httpClient: HttpClient, private datePipe: DatePipe) {
  }

  /**
   * This method is set to provide search results from backend, providing a query that would be used by it to request the EPO API
   * @param keywordsQuery
   * @param applicant
   * @param publicationDateStart
   * @param publicationDateEnd
   */
  queryData(keywordsQuery: any, applicant: any, publicationDateStart: Date, publicationDateEnd: Date) {

    let publicationDateStartFormated: string | null = "";
    let publicationDateEndFormated: string | null = "";
    let applicantQuery: String | undefined = undefined;
    let periodQuery: String | undefined = undefined;
    let query: String | undefined = "";

    //we reset the searchResults
    this.searchResults.setResults([]);

    // we reset the currentQuery
    this.currentQuery = "";

    // prepare applicant query
    if (applicant != "") {
      applicantQuery = "pa=" + applicant;
    }

    // prepare period query
    if (publicationDateStart != undefined && publicationDateEnd != undefined) {
      publicationDateStartFormated = this.datePipe.transform(publicationDateStart, 'yyyyMMdd');
      publicationDateEndFormated = this.datePipe.transform(publicationDateEnd, 'yyyyMMdd');

      periodQuery = "pd=" + "\"" + publicationDateStartFormated + " " + publicationDateEndFormated + "\"";
    }

    // Prepare the query depending on the existence of the parameters.
    // for this first one, we have all parameters.
    if (keywordsQuery != "" && applicantQuery != undefined && periodQuery != undefined) {
      query = keywordsQuery + "," + applicantQuery + "," + periodQuery;


    //  In this case, we have keywords and period
    } else if (keywordsQuery != "" && applicantQuery == undefined && periodQuery != undefined) {
      query = keywordsQuery + "," + periodQuery;

    // In this one, we have keywords and applicant name
    } else if (keywordsQuery != "" && applicantQuery != undefined && periodQuery == undefined) {
      query = keywordsQuery + "," + applicantQuery;

    //In this case, we have only keywords
    } else if (keywordsQuery != "" && applicantQuery == undefined && periodQuery == undefined) {
      query = keywordsQuery;

    //  In this case, we have only applicant name
    } else if (keywordsQuery == "" && applicantQuery != undefined && periodQuery == undefined) {
      query = applicantQuery;

    //  Here, we only have a period
    } else if (keywordsQuery == "" && applicantQuery == undefined && periodQuery != undefined) {
      query = periodQuery;

    // In this case, we have an applicant name and a period
    } else if (keywordsQuery == "" && applicantQuery != undefined && periodQuery != undefined) {
      query = applicantQuery + "," + periodQuery;
    }

    //we keep the query as the current one
    this.currentQuery = query;

    //We request the back with the builded query
    let searchRequestUrl: string = this.PATENT_SEARCH_API + query + "/1";
    this.httpClient.get<PatentModel[]>(searchRequestUrl).subscribe({
      next: (patents) => {

        //we set the results on searchResults
        this.searchResults.setResults(patents);
        this.internalError.internalError = false;
      },
      error: (err) => {
        console.error(err)
        this.internalError.internalError = true;
      }
    })
  }

  /**
   * method to query next result page
   * @param page
   */
  queryNextPage(page: number) {

    //set the page number
    let nextPageNumber: string = "/" + page;

    //reset searchResults
    this.searchResults.setResults([]);

    //request back with the current query
    let searchRequestUrl: string = this.PATENT_SEARCH_API + this.currentQuery + nextPageNumber;
    this.httpClient.get<PatentModel[]>(searchRequestUrl).subscribe({
      next: (patents) => {
        // set the results on searchResults
        this.searchResults.setResults(patents);
      },
      error: (err) => console.error(err)
    })
  }
}

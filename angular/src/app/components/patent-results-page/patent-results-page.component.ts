import {Component, Input, OnInit} from '@angular/core';
import {PatentModel} from "../../model/patent-model";
import {QueryService} from "../../services/query.service";
import {SearchResults} from "../../model/search-results";
import  {InternalError} from "../../model/internal-error";


@Component({
    selector: 'app-patent-results-page',
    templateUrl: './patent-results-page.component.html',
    styleUrls: ['./patent-results-page.component.scss']
})

export class PatentResultsPageComponent implements OnInit {

    @Input()
    public searchResults: SearchResults;

    public innerWidth: any;

    @Input()
    patent: PatentModel | undefined;

    currentPage: number = 1;
    internalError: InternalError;

  constructor(public queryService: QueryService) {
      this.searchResults = queryService.searchResults
      this.internalError = queryService.internalError;
    }

    ngOnInit(): void {
        this.innerWidth = window.innerWidth;
    }

    nextPage(pageNumber: number) {
      this.queryService.queryNextPage(pageNumber);
      this.currentPage = pageNumber;
    }

    previousPage(pageNumber: number) {
      this.queryService.queryNextPage(pageNumber);
      this.currentPage = pageNumber;
    }

    retrievePatentDetails(patent: PatentModel) {
      this.patent = patent;
    }
}

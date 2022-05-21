import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import {PatentModel} from "../../model/patent-model";
import {SearchResults} from "../../model/search-results";
import {FavoritePatentService} from "../../services/favorite-patent.service";

@Component({
    selector: 'app-patent-box',
    templateUrl: './patent-box.component.html',
    styleUrls: ['./patent-box.component.scss'],
})
export class PatentBoxComponent implements OnInit {

    @Output()
    public patentBoxClick: EventEmitter<PatentModel> = new EventEmitter<PatentModel>();

    @Output()
    public innerWidth: any;

    @Input()
    public searchResults: SearchResults | undefined;

    message: String = "";
    selectedIndex: number = -1;

    constructor(private favoritePatentService: FavoritePatentService) {}

    ngOnInit(): void {
      this.innerWidth = window.innerWidth;
    }

    addFavoritePatent(index: number) {
        let favoritePatentModel: PatentModel | undefined = this.searchResults?.getResults()[index];
        if(favoritePatentModel) {
          favoritePatentModel.favorite = !favoritePatentModel.favorite ;
          this.favoritePatentService.toogleFavoritePatent(favoritePatentModel).subscribe(
            {
              next: (isFavorite) => {
                if (favoritePatentModel != undefined) favoritePatentModel.favorite = isFavorite;
              }
            }
          )
        }
    }

    sendPatent(index: number) {
      this.selectedIndex = index;
      this.patentBoxClick.emit(this.searchResults?.getResults()[index]);
    }
}


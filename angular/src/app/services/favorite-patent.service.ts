import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {FavoritePatent} from '../model/favorite-patent';
import {Observable} from 'rxjs';
import {PatentInfo} from '../model/patent-info';
import {PatentModel} from "../model/patent-model";
import {environment} from "../../environments/environment";

/**
 * Service for favorite patent treatment
 */
@Injectable({
  providedIn: 'root'
})
export class FavoritePatentService {

  PATENT_API: string = environment.PATENT_API;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private FAVORITE_PATENT_API: String = environment.FAVORITE_PATENT_API;

  constructor(private http: HttpClient) {
  }

  /**
   * get all favorite patent for the user
   */
  getAllByUser(): Observable<FavoritePatent[]> {
    return this.http.get<FavoritePatent[]>(this.FAVORITE_PATENT_API + "/all");
  }

  /**
   * update a comment in a favorite patent
   * @param favoritePatent
   */
  updateComment(favoritePatent: FavoritePatent): Observable<FavoritePatent> {
    return this.http.put<FavoritePatent>(this.FAVORITE_PATENT_API + "/edit", favoritePatent);
  }

  /**
   * get patent info from the back
   * @param publicationCode
   */
  getPatentInfo(publicationCode: string): Observable<PatentInfo> {
    return this.http.get<PatentInfo>(this.PATENT_API + "/" + publicationCode);
  }

  /**
   * delete a favorite patent
   * @param id
   */
  deleteFavoritePatent(id: number) {
    return this.http.delete(this.FAVORITE_PATENT_API + "/delete/" + id);
  }

  /**
   * add or remove a favorite patent from the search result panel
   * @param favoritePatentModel
   */
  toogleFavoritePatent(favoritePatentModel: PatentModel): Observable<boolean> {
    let title = favoritePatentModel.title;
    return this.http.post<boolean>(this.FAVORITE_PATENT_API + "/toogle/" + favoritePatentModel.patentNumber, {
      title,
    }, this.httpOptions)
  }
}

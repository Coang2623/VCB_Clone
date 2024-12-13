import { Injectable } from '@angular/core';
import {HttpBaseService} from "../http-base-service/http-base.service";
import {Observable} from "rxjs";
import {APIResponseModel} from "../../model/interface";
import {environment} from "../../../environments/environment.development";
import {Article} from "../../model/class";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private httpBaseService: HttpBaseService) { }

  getAllArticlesFullInfo(page: number = 0, size: number = 10): Observable<APIResponseModel> {
    const params: { [key: string]: string } = {
      page: page.toString(),
      size: size.toString(),
    };

    return this.httpBaseService.get(environment.httpBaseUrl + 'article/all', params);
  }

  getAllArticlesBasicInfo(page: number = 0, size: number = 10): Observable<APIResponseModel> {
    const params: { [key: string]: string } = {
      page: page.toString(),
      size: size.toString(),
    };

    return this.httpBaseService.get(environment.httpBaseUrl + 'article/all-basic', params);
  }

  searchArticle(page: number = 0, size: number = 10, searchValue: string, sectionName: string): Observable<APIResponseModel> {
    const params: { [key: string]: string } = {
      page: page.toString(),
      size: size.toString(),
      keyword: searchValue,
      sectionName: sectionName,
    };

    return this.httpBaseService.get(environment.httpBaseUrl + 'article/search', params);
  }

  deleteArticle(id: number): Observable<APIResponseModel> {
    return this.httpBaseService.delete(environment.httpBaseUrl + 'article/' + id);
  }

  createArticle(article: Article): Observable<APIResponseModel> {
    return this.httpBaseService.post(environment.httpBaseUrl + 'article', article);
  }

  updateArticle(article: Article): Observable<APIResponseModel> {
    return this.httpBaseService.put(environment.httpBaseUrl + 'article', article);
  }

  changeStatus(id: number, status: string): Observable<APIResponseModel> {
    return this.httpBaseService.put(environment.httpBaseUrl + 'article/change-status/' + id + '/' + status, null);
  }
}

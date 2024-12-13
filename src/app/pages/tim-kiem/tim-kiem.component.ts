import {Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";
import {ArticleService} from "../../services/article-service/article.service";
import {APIResponseModel, PaginatedResult} from "../../model/interface";
import {Article} from "../../model/class";
import {FormsModule} from "@angular/forms";
import {BehaviorSubject, Observable} from "rxjs";

@Component({
  selector: 'app-tim-kiem',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './tim-kiem.component.html',
  styleUrl: './tim-kiem.component.css'
})
export class TimKiemComponent implements OnInit{

  sectionList: string[] = ['Sản Phẩm - Giải Pháp', 'Ưu đãi', 'Tin tức', 'Hướng dẫn sử dụng', 'Khác'];

  page: number = 0;
  size: number = 10;

  searchValue: string = '';
  searchValueValid: boolean = true;
  sectionName: string = '';

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private articleService: ArticleService) {
  }

  toggleDrop: boolean = false;
  paginateObj: PaginatedResult = {} as PaginatedResult;
  articleList: Article[] = [];

  loadArticle(page: number = 0, size: number = 10) {
    this.articleService.getAllArticlesBasicInfo(page, size).subscribe({
      next: (data: APIResponseModel) => {
        this.paginateObj = data.result;
        this.articleList = this.paginateObj.content;

        this.page = this.paginateObj.pageable.pageNumber + 1;
        this.size = this.paginateObj.pageable.pageSize;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  ngOnInit(): void {// Lấy giá trị query từ URL
    this.activatedRoute.queryParams.subscribe(params => {
      this.searchValue = params['keyword'] || '';
      // Gọi hàm tìm kiếm API hoặc xử lý dữ liệu
      this.searchArticle();
    });
  }

  searchArticle(page: number = 0, size: number = 10) {
    if(this.searchValue.length < 3){
      this.searchValueValid = false;
    } else {
      this.searchValueValid = true;
      this.articleService.searchArticle(page, size, this.searchValue, this.sectionName).subscribe({
        next: (data: APIResponseModel) => {
          this.paginateObj = data.result;
          this.articleList = this.paginateObj.content;

          this.page = this.paginateObj.pageable.pageNumber + 1;
          this.size = this.paginateObj.pageable.pageSize;
        },
        error: (error) => {
          console.log(error);
        }
      })
    }
  }
}

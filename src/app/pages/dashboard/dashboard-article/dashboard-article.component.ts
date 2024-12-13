import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ConfirmModalComponent} from "../../shared-component/confirm-modal/confirm-modal.component";
import {FieldConfig, FormComponent} from "../../shared-component/form/form.component";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {RoleService} from "../../../services/role-service/role.service";
import {Role, Article} from "../../../model/class";
import {FormsModule, Validators} from "@angular/forms";
import {ArticleService} from "../../../services/article-service/article.service";
import {PaginatedResult} from "../../../model/interface";

@Component({
  selector: 'app-dashboard-article',
  standalone: true,
  imports: [
    ConfirmModalComponent,
    FormComponent,
    NgForOf,
    NgIf,
    FormsModule,
    NgClass
  ],
  templateUrl: './dashboard-article.component.html',
  styleUrl: './dashboard-article.component.css'
})
export class DashboardArticleComponent implements OnInit{

  @Output() changeActiveLinkEvent = new EventEmitter<string>();

  constructor(private articleService: ArticleService, private roleService: RoleService) {}

  currentPage: number = 1;
  pageSize: number = 10;
  paginate: PaginatedResult = {} as PaginatedResult;

  list: Article[] = [];
  createObj: Article = new Article();
  deleteObj: Article = new Article();
  updateObj: Article = new Article();

  showConfirmModal: boolean = false;
  showHideArticleConfirmModal: boolean = false;
  showActiveArticleConfirmModal: boolean = false;
  showCreateForm: boolean = false;
  showUpdateForm: boolean = false;

  articleCreateFormFields: FieldConfig[] = [];
  articleUpdateFormFields: FieldConfig[] = [];



  ngOnInit(): void {
    this.changeActiveLinkEvent.emit('Articles');
    this.loadArticle();

    this.articleCreateFormFields = this.generateArticleCreateFormFields()
    this.articleUpdateFormFields = this.generateArticleUpdateFormFields()
  }

  loadArticle(page = 0, size = 10) {
    this.articleService.getAllArticlesFullInfo(page, size).subscribe({
      next: (value) => {
        this.paginate = value.result;

        this.currentPage = this.paginate.pageable.pageNumber + 1;
        this.pageSize = this.paginate.pageable.pageSize;
        },
      error: (error) => {
        console.log(error);
      },
    });
  }

  generateArticleCreateFormFields(): FieldConfig[] {
    return [
      {
        name: 'articleTitle',
        label: 'Title',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'articleSummary',
        label: 'Summary',
        type: 'textArea',
        validators: [
          Validators.required, Validators.minLength(8)
        ]
      },
      {
        name: 'articleAuthor',
        label: 'Author',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'articleSectionName',
        label: 'Section',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'articleContent',
        label: 'Content',
        type: 'textArea',
        validators: [
          Validators.required
        ]
      }
    ]
  }

  generateArticleUpdateFormFields(): FieldConfig[] {
    return [
      {
        name: 'articleTitle',
        label: 'Title',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'articleSummary',
        label: 'Summary',
        type: 'textArea',
        validators: [
          Validators.required, Validators.minLength(8)
        ]
      },
      {
        name: 'articleAuthor',
        label: 'Author',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'articleSectionName',
        label: 'Section',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'articleContent',
        label: 'Content',
        type: 'textArea',
        validators: [
          Validators.required
        ]
      }
    ]
  }

  onDeleteSubmit() {
    this.articleService.deleteArticle(this.deleteObj.articleId).subscribe({
      next: (value) => {
        if (value.code == 1000) {
          this.loadArticle(this.currentPage - 1, this.pageSize);
        }
      },
      error: (error) => {
        console.log(error);
      },
    })
  }

  onCreateSubmit(event: any) {
    Object.assign(this.createObj, event);
    console.log(this.createObj);
    this.createObj.articleStatus = 'ACTIVE';
    this.articleService.createArticle(this.createObj).subscribe({
      next: (value) => {
        if (value.code == 1000) {
          this.loadArticle(this.currentPage - 1, this.pageSize);
        }
      },
      error: (error) => {
        console.log(error);
      },
    })
  }

  onUpdateSubmit(event: any) {
    Object.assign(this.updateObj, event);
    console.log(this.updateObj);
    this.articleService.updateArticle(this.updateObj).subscribe({
      next: (value) => {
        if (value.code == 1000) {
          this.loadArticle(this.currentPage - 1, this.pageSize);
        }
      },
      error: (error) => {
        console.log(error);
      },
    })
  }

  changeStatus(status: string) {
    this.articleService.changeStatus(this.updateObj.articleId, status).subscribe({
      next: (value) => {
        if (value.code == 1000) {
          this.loadArticle(this.currentPage - 1, this.pageSize);
        }
      },
      error: (error) => {
        console.log(error);
      },
    })
    this.showActiveArticleConfirmModal = false;
    this.showHideArticleConfirmModal = false;
  }
}

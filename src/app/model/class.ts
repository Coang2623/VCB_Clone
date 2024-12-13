export class Role{
  roleName: string = '';
  roleDescription: string = '';
  Permissions: any[] = [];
  roleCreatedAt: string = '';
  roleUpdatedAt: string = '';
  roleCreatedBy: string = '';
  roleUpdatedBy: string = '';
}

export class User{
  userId: number = 0;
  userName: string = '';
  userPassword: string = '';
  userFirstName: string = '';
  userLastName: string = '';
  userEmail: string = '';
  userDateOfBirth: string = '';
  userStatus: string = '';
  roles: Role[] = [];
  userCreatedDate: string = '';
  userUpdatedDate: string = '';
  userCreatedBy: string = '';
  userUpdatedBy: string = '';
}

export class Article{
  articleId: number = 0;
  articleTitle: string = '';
  articleSummary: string = '';
  articleAuthor: string = '';
  articleSectionName: string = '';
  articleContent: string = '';
  articleStatus: string = '';
  articleCreatedAt: string = '';
  articleUpdatedAt: string = '';
  articleCreatedBy: string = '';
  articleUpdatedBy: string = '';
}

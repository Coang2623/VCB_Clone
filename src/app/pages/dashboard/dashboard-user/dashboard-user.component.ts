import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {UserService} from "../../../services/user-service/user.service";
import {Role, User} from "../../../model/class";
import {ConfirmModalComponent} from "../../shared-component/confirm-modal/confirm-modal.component";
import {FieldConfig, FormComponent} from "../../shared-component/form/form.component";
import {Validators} from "@angular/forms";
import {RoleService} from "../../../services/role-service/role.service";
import {BehaviorSubject} from "rxjs";

export interface RoleAndStatus {
  listRole: Role[];
  listStatus: String[];
}

@Component({
  selector: 'app-dashboard-user',
  standalone: true,
  imports: [
    CommonModule,
    ConfirmModalComponent,
    FormComponent
  ],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent implements OnInit{
  @Output() changeActiveLinkEvent = new EventEmitter<string>();

  constructor(private userService: UserService, private roleService: RoleService) {}

  list:User[] = [];
  listRoleAndStatus: BehaviorSubject<RoleAndStatus> = new BehaviorSubject<RoleAndStatus>({listRole: [], listStatus: []})
  listRole: Role[] = [];
  listString: String[] = [];
  deleteObj: User = new User();
  showConfirmModal: boolean = false;
  showDisableUserConfirmModal: boolean = false;
  showActiveUserConfirmModal: boolean = false;
  showCreateForm: boolean = false;
  showUpdateForm: boolean = false;

  userFormFields: FieldConfig[] = [];
  userUpdateFormFields: FieldConfig[] = [];
  userCreate: User = new User();
  userUpdate: User = new User();

  ngOnInit(): void {
    this.changeActiveLinkEvent.emit('Users');
    this.loadUser()
    this.loadRole()
    this.loadUserStatus()

    // Lắng nghe thay đổi của listRole và cập nhật options trong userFormFields
    this.listRoleAndStatus.subscribe(({listRole, listStatus}) => {
      this.userFormFields = this.generateFormFields(listRole, listStatus);
      this.userUpdateFormFields = this.generateUpdateFormFields(listRole, listStatus);
    })
  }

  loadRole(){
    this.roleService.getAllRolesBasicInfo().subscribe({
      next: (value) => {
        this.listRole = value.result;
        this.listRoleAndStatus.next({listRole: this.listRole, listStatus: this.listString});
        },
      error: (error) => {
        console.log(error);
        },
    });
  }

  loadUserStatus(){
    this.userService.getAllUserStatus().subscribe({
      next: (value) => {
        this.listString = value.result;
        this.listRoleAndStatus.next({listRole: this.listRole, listStatus: this.listString});
        },
      error: (error) => {
        console.log(error);
        },
    })
  }

  loadUser(){
    this.userService.getAllUsersWithFullInfo().subscribe({
      next: (value) => {this.list = value.result;},
      error: (error) => {
        console.log(error);
      },
    });
  }

  deleteUser(){
    this.userService.deleteUser(this.deleteObj.userId).subscribe({
      next: (value) => {
        if(value.code == 1000){
          this.loadUser();
        }
      },
      error: (error) => {console.log(error);},
    });
    this.showConfirmModal = false;
  }

  generateFormFields(roles: Role[], status: String[]): FieldConfig[] {
    return [
      {
        name: 'userName',
        label: 'Username',
        type: 'text',
        validators: [
          Validators.required, Validators.minLength(5)
        ]
      },
      {
        name: 'userPassword',
        label: 'Password',
        type: 'password',
        validators: [
          Validators.required, Validators.minLength(8)
        ]
      },
      {
        name: 'userFirstName',
        label: 'First name',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userLastName',
        label: 'Last name',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userEmail',
        label: 'Email',
        type: 'email',
        validators: [
          Validators.required, Validators.email
        ]
      },
      {
        name: 'userDateOfBirth',
        label: 'Date of birth',
        type: 'text',
        pattern: '^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userStatus',
        label: 'Status',
        type: 'dropdown',
        options: status.map(s => s.toString()),
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userRole',
        label: 'Role',
        type: 'dropdown',
        options: roles.map(role => role.roleName),
        validators: [
          Validators.required
        ]
      }
    ]
  }

  generateUpdateFormFields(roles: Role[], status: String[]): FieldConfig[] {
    return [
      {
        name: 'userName',
        label: 'Username',
        type: 'text',
        validators: [
          Validators.required, Validators.minLength(5)
        ]
      },
      {
        name: 'userPassword',
        label: 'Password',
        type: 'password',
        validators: [
          Validators.minLength(8)
        ]
      },
      {
        name: 'userFirstName',
        label: 'First name',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userLastName',
        label: 'Last name',
        type: 'text',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userEmail',
        label: 'Email',
        type: 'email',
        validators: [
          Validators.required, Validators.email
        ]
      },
      {
        name: 'userDateOfBirth',
        label: 'Date of birth',
        type: 'text',
        pattern: '^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$',
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userStatus',
        label: 'Status',
        type: 'dropdown',
        options: status.map(s => s.toString()),
        validators: [
          Validators.required
        ]
      },
      {
        name: 'userRole',
        label: 'Role',
        type: 'dropdown',
        options: roles.map(role => role.roleName),
        validators: [
        ]
      }
    ]
  }

  onCreateSubmit(event: any) {
    Object.assign(this.userCreate, event);
    this.userCreate.roles = this.listRoleAndStatus.getValue().listRole.filter(role => event.userRole.includes(role.roleName));
    this.userService.createUser(this.userCreate).subscribe({
      next: (value) => {
        if(value.code == 1000){
          this.loadUser();
        }
      },
      error: (error) => {console.log(error);},
    });
    this.showCreateForm = false
  }

  onUpdateSubmit(event: any) {
    Object.assign(this.userUpdate, event);
    this.userUpdate.roles = this.listRoleAndStatus.getValue().listRole.filter(role => event.userRole.includes(role.roleName));
    this.userService.updateUser(this.userUpdate).subscribe({
      next: (value) => {
        if(value.code == 1000){
          this.loadUser();
        }
      },
      error: (error) => {console.log(error);},
    });
    this.userUpdate = new User();
    this.showUpdateForm = false
  }

  onDisableUserClick() {
    this.userUpdate.userStatus = 'DISABLED';
    this.userService.updateUser(this.userUpdate).subscribe({
      next: (value) => {
        if(value.code == 1000){
          this.loadUser();
        }
      },
      error: (error) => {console.log(error);},
    });
    this.userUpdate = new User();
    this.showDisableUserConfirmModal = false
  }

  onActiveUserClick() {
    this.userUpdate.userStatus = 'ACTIVE';
    this.userService.updateUser(this.userUpdate).subscribe({
      next: (value) => {
        if(value.code == 1000){
          this.loadUser();
        }
      },
      error: (error) => {console.log(error);},
    });
    this.userUpdate = new User();
    this.showActiveUserConfirmModal = false
  }
}

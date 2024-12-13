import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";

export interface FieldConfig {
  name: string; // Tên trường (FormControlName)
  label: string; // Label hiển thị
  type: 'text' | 'email' | 'number' | 'dropdown' | 'password' | 'textArea'; // Loại trường
  options?: string[]; // Các tùy chọn nếu là dropdown
  validators?: any[]; // Danh sách validator (Validators.required, Validators.minLength...)
  pattern?: string; // Pattern cấu hình
}

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form.component.html',
  styleUrl: './form.component.css'
})

export class FormComponent implements OnChanges{
  @Input() fields: FieldConfig[] = []; // Danh sách cấu hình các trường
  @Input() data: any = {};
  @Input() header: string = 'Form';
  @Input() btnContent: string = 'Submit';
  @Output() formSubmit = new EventEmitter<any>(); // Sự kiện submit form
  @Output() formCancel = new EventEmitter<void>();



  constructor(private fb: FormBuilder) {}

  form!: FormGroup;

  ngOnChanges() {
    this.initializeForm();
  }

  private initializeForm() {
    const controls: { [key: string]: any } = {};
    this.fields.forEach((field) => {
      controls[field.name] = [
        this.data ? this.data[field.name] : '',
        field.validators || [] // Áp dụng validators từ cấu hình
      ];
    });
    this.form = this.fb.group(controls);
  }

  getMinLength(validators: any[] | undefined): number | null {
    if (!validators) return null; // Kiểm tra nếu validators không tồn tại
    const minLengthValidator = validators.find(v => v.minlength);
    return minLengthValidator ? minLengthValidator.minlength : null;
  }

  onSubmit() {
    if (this.form.valid) {
      this.formSubmit.emit(this.form.value); // Emit dữ liệu khi form hợp lệ
    }
  }
}

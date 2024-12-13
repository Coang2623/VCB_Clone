import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-confirm-modal',
  standalone: true,
  imports: [],
  templateUrl: './confirm-modal.component.html',
  styleUrl: './confirm-modal.component.css'
})
export class ConfirmModalComponent {
  @Input() title: string = 'Xác nhận'; // Tiêu đề modal
  @Input() message: string = 'Bạn có chắc chắn muốn thực hiện hành động này?'; // Nội dung
  @Input() confirmText: string = 'Đồng ý'; // Nút xác nhận
  @Input() cancelText: string = 'Hủy'; // Nút hủy

  @Output() confirmed = new EventEmitter<void>(); // Sự kiện xác nhận
  @Output() canceled = new EventEmitter<void>(); // Sự kiện hủy

  onConfirm() {
    this.confirmed.emit();
  }

  onCancel() {
    this.canceled.emit();
  }
}

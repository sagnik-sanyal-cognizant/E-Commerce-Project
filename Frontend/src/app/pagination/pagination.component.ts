import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {

  // Input properties to receive current page and total pages
  @Input() currentPage: number = 1;
  @Input() totalPages: number = 1;
  @Output() pageChange = new EventEmitter<number>(); // Output event emitter to notify

  // Getter to generate an array of page numbers based on total pages
  get pageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1)
  }

  // Method to emit the page change event with the selected page number
  changePage(page: number): void {
    this.pageChange.emit(page)
  }

}
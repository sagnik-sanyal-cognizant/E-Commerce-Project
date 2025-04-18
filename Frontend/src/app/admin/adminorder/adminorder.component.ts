import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PaginationComponent } from '../../pagination/pagination.component';
import { ApiService } from '../../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminorder',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, PaginationComponent],
  templateUrl: './adminorder.component.html',
  styleUrl: './adminorder.component.css'
})
export class AdminorderComponent implements OnInit {
  orders: any[] = []
  filterOrders: any[] = []
  statusFilter: string = ''
  searchStatus: string = ''
  currentPage: number = 1;
  totalPages: number = 1;
  itemsPerPage: number = 10;
  error: any = null

  // Array to hold possible order statuses
  OderStatus = ["PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED", "RETURNED"]

  constructor(
    private apiService: ApiService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fetchOrders();
  }

  // Method to fetch orders from the API based on the search status
  fetchOrders(): void {
    const ordersObservable = this.searchStatus ? this.apiService.getAllOrderItemsByStatus(this.searchStatus)
      : this.apiService.getAllOrders();

    ordersObservable.subscribe({
      next: (res) => {
        this.setOrders(res.orderItemList || [])
      },
      error: (error) => {
          console.log(error)
      }
    })
  }

  // Method to set orders and update pagination details
  setOrders(orderList: any[]): void {
    this.orders = orderList
    this.totalPages = Math.ceil(orderList.length / this.itemsPerPage);
    this.filterOrders = orderList.slice(
      (this.currentPage - 1) * this.itemsPerPage,
      this.currentPage * this.itemsPerPage
    )
  }

  // Method to handle changes in the status filter
  handleFilterChange(): void {
    this.currentPage = 1;
    if (this.statusFilter) {
      const filtered = this.orders.filter(order => order.status == this.statusFilter);
      this.filterOrders = filtered.slice(0, this.itemsPerPage)
      this.totalPages = Math.ceil(filtered.length / this.itemsPerPage);
    } else {
      this.filterOrders = this.orders.slice(0, this.itemsPerPage)
      this.totalPages = Math.ceil(this.orders.length / this.itemsPerPage);
    }
  }

  // Method to handle changes in the search status
  handleSearchStatusChanged():void{
    this.currentPage = 1;
    this.fetchOrders();
  }

  // Method to handle page changes for pagination
  onPageChange(page: number):void{
    this.currentPage = page
    this.filterOrders = this.orders.slice((this.currentPage -1) * this.itemsPerPage, this.currentPage * this.itemsPerPage)
  }

  // Method to navigate to the order details page
  navigateToOrderDetailsPage(id: number):void{
    this.router.navigate([`/admin/order-details/${id}`])
  }

}
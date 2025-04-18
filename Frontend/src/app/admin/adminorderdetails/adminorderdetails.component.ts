import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-adminorderdetails',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './adminorderdetails.component.html',
  styleUrl: './adminorderdetails.component.css'
})
export class AdminorderdetailsComponent implements OnInit {
  orderItem: any[] = [];
  selectedStatus: { [key: number]: string } = {};
  orderId: any = '';
  message: any = null;

  // Array to hold possible order statuses
  OderStatus = ["PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED", "RETURNED"];

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    // Get the order ID from the route parameters
    this.orderId = this.route.snapshot.paramMap.get('orderId');
    this.fetchOrderDetails();
  }

  // Get the order ID from the route parameters
  fetchOrderDetails(): void {
    if (this.orderId) {
      this.apiService.getOrderItemById(this.orderId).subscribe({
        next: (res) => {
          this.orderItem = res.orderItemList || [];
          this.orderItem.forEach(item => {
            this.selectedStatus[item.id] = item.status;
            this.fetchUserAddress(item.user.id);
          });
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  // Method to fetch user address from the API
  fetchUserAddress(userId: string): void {
    this.apiService.getAddressByUserId(userId).subscribe({
      next: (addresses) => {
        this.orderItem.forEach(item => {
          if (item.user.id === userId) {
            item.user.address = addresses[0]; // Only the first address
          }
        });
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  // Method to handle status change for an order item
  handleStatusChange(orderId: number, newStatus: string): void {
    this.selectedStatus[orderId] = newStatus;
  }


  // Method to submit the status change for an order item
  handleSubmitStatusChange(orderId: number): void {
    this.apiService.updateOrderItemStatus(orderId.toString(), this.selectedStatus[orderId]).subscribe({
      next: (res) => {
        this.fetchOrderDetails();
        this.message = "Order item status was successfully updated";
        setTimeout(() => {
          this.message = null;
        }, 4000);
      }
    });
  }

  // Method to convert base64 string to URL
  convertImageDataToUrl(imageData: string): string {
    const byteArray = Uint8Array.from(atob(imageData), c => c.charCodeAt(0));
    const blob = new Blob([byteArray], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
  }

}
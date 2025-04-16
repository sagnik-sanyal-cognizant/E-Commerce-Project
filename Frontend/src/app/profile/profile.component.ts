import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PaginationComponent } from '../pagination/pagination.component';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, PaginationComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {
  constructor(private apiService: ApiService, private router: Router) {}

  userInfo: any = null;
  error: any = null;
  currentPage: number = 1;
  itemsPerPage: number = 5;

  ngOnInit(): void {
    this.fetchUserInfo();
  }

  fetchUserInfo(): void {
    this.apiService.getLoggedInUserInfo().subscribe({
      next: (response) => {
        this.userInfo = response.user;
        this.fetchUserAddress(this.userInfo.id);
      },
      error: (error) => {
        console.log(error);
        this.error =
          error?.error?.message || 'Unable to fetch user information';
      },
    });
  }

  fetchUserAddress(userId: string): void {
    this.apiService.getAddressByUserId(userId).subscribe({
      next: (addresses) => {
        this.userInfo.address = addresses[0];
      },
      error: (error) => {
        console.log(error);
        this.error = error?.error?.message || 'Unable to fetch user address';
      },
    });
  }

  handleAddressClick(): void {
    const urlPathToNavigateTo = this.userInfo?.address? '/edit-address': '/add-address';
    this.router.navigate([urlPathToNavigateTo], {
      state: { address: this.userInfo.address },
    });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  get paginatedOrders(): any[] {
    if (!this.userInfo?.orderItemList) return [];

    return this.userInfo.orderItemList.slice(
      (this.currentPage - 1) * this.itemsPerPage,
      this.currentPage * this.itemsPerPage
    );
  }

  get totalPages(): number {
    return Math.ceil(
      (this.userInfo?.orderItemList?.length || 0) / this.itemsPerPage
    );
  }

  // Method to convert base64 string to URL
  convertImageDataToUrl(imageData: string): string {
    const byteArray = Uint8Array.from(atob(imageData), c => c.charCodeAt(0));
    const blob = new Blob([byteArray], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
  }
}

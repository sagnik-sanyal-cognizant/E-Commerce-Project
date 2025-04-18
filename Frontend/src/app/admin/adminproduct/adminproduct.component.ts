import { Component, OnInit } from '@angular/core';
import { PaginationComponent } from '../../pagination/pagination.component';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminproduct',
  standalone: true,
  imports: [PaginationComponent, CommonModule],
  templateUrl: './adminproduct.component.html',
  styleUrl: './adminproduct.component.css'
})
export class AdminproductComponent implements OnInit {

  constructor(
    private apiService: ApiService,
    private router: Router
  ) { }
  products: any[] = [];
  currentPage: number = 1;
  totalPages: number = 0;
  itemsPerPage: number = 5; // Pagination 5 items per page
  error: string = ''

  ngOnInit(): void {
    this.fetchProducts();
  }

  // Method to fetch products from the API
  fetchProducts(): void {
    const productObservable = this.apiService.getAllProducts();

    productObservable.subscribe({
      next: (response) => {
        if (response?.productList && response.productList.length > 0) {
          this.handleProductResponse(response.productList)
        } else {
          this.error = 'Product Not Found'
        }
      },
      error: (error) => {
        console.log(error)
        this.error = "Error getting products due to backend not started!";
      }
    })

  }

  // Method to handle the product response and update pagination details
  handleProductResponse(products: []): void {
    this.totalPages = Math.ceil(products.length / this.itemsPerPage);
    this.products = products.slice(
      (this.currentPage - 1) * this.itemsPerPage,
      this.currentPage * this.itemsPerPage
    )
    console.log(this.products)
  }


  // Method to handle page changes for pagination
  onPageChange(page: number): void {
    this.currentPage = page;
    this.fetchProducts();
  }

  // Method to handle the edit action for a product
  handleEdit(productId: string): void {
    this.router.navigate([`/admin/edit-product/${productId}`])
  }


  // Method to navigate to the add product page
  goToAddProduct(): void {
    this.router.navigate([`/admin/add-product`])
  }

  // Method to handle the delete action for a product
  handleDelete(id: string): void {
    const confirm = window.confirm("Are you sure you want to delete this product?")
    if (confirm) {
      this.apiService.deletProduct(id).subscribe({
        next: () => {
          this.fetchProducts();
        },
        error: (error) => {
          console.log(error)
        }
      })
    }
  }

}
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PaginationComponent } from '../pagination/pagination.component';
import { ProductlistComponent } from '../productlist/productlist.component';
import { ApiService } from '../service/api.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-categoryproducts',
  standalone: true,
  imports: [CommonModule, PaginationComponent, ProductlistComponent],
  templateUrl: './categoryproducts.component.html',
  styleUrl: './categoryproducts.component.css'
})
export class CategoryproductsComponent implements OnInit {

  constructor(
    private apiService: ApiService,
    private route:ActivatedRoute
  ){}
  products: any[] = [];
  currentPage = 1;
  totalPages = 0;
  itemsPerPage = 5; // Pagination details 5items per page
  error: any = null;

  ngOnInit(): void {
    // Subscribe to route parameters to get the category ID
    this.route.params.subscribe(params =>{
      const categoryId = params['categoryId']
      if (categoryId) {
        this.fetchProductsByCategory(categoryId);
      }
    })
  }

  // Method to fetch products by category ID from the API
  fetchProductsByCategory(categoryId: string): void{
    this.apiService.getAllProductsByCategotyId(categoryId).subscribe({
      next: (response) =>{
        const allProducts = response.productList || []
        // Calculate total pages based on the number of products and items per page
        this.totalPages = Math.ceil(allProducts.length / this.itemsPerPage);
        // Slice the products array to get the products for the current page
        this.products = allProducts.slice((this.currentPage -1) * this.itemsPerPage, this.currentPage * this.itemsPerPage);
      },
      error:(error) =>{
        this.error = "Unable to Fetch all products by category id";
      }
    })
  }

  // Method to handle page changes for pagination
  onPageChange(page: number):void{
    this.currentPage = page;
    const categoryId = this.route.snapshot.params['categoryId']
    this.fetchProductsByCategory(categoryId); // Fetch products for the new page
  }

}
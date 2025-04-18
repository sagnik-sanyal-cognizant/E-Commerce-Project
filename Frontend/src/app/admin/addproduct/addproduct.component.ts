import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addproduct',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './addproduct.component.html',
  styleUrl: './addproduct.component.css'
})
export class AddproductComponent implements OnInit {

  constructor(
    private apiService: ApiService,
    private router: Router
  ) { }

  image: any = null;
  categories: any[] = [];
  categoryId: Number | null = null;
  name: string = '';
  description: string = '';
  price: string = '';
  quantity: string = '';
  discount: string = '';
  message: string = '';

  ngOnInit(): void {
    this.apiService.getAllCategory().subscribe({ // Fetch all categories from the API
      next: (response) => {
        this.categories = response.categoryList || [];
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  // Method to handle image file selection
  handleImage(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.image = input.files[0];
    }
  }

  // Method to handle form submission for adding a product
  handleSubmit(): void {
    if (!this.image || !this.categoryId || !this.name || !this.description || !this.price || !this.quantity || !this.discount) {
      this.message = "Please fill in all fields!";
      return;
    }

    // Create a FormData object to hold the product details
    const formData = new FormData();
    formData.append('image', this.image);
    formData.append('categoryId', this.categoryId.toString());
    formData.append('name', this.name);
    formData.append('description', this.description);
    formData.append('price', this.price);
    formData.append('quantity', this.quantity);
    formData.append('discount', this.discount);

    // Send the product details to the API
    this.apiService.addProduct(formData).subscribe({
      next: (res) => {
        this.message = res.message;
        setTimeout(() => {
          this.router.navigate(['/admin/products']);
        }, 3000);
      },
      error: (error) => {
        console.log(error);
        this.message = "Please fill as per format!";
      }
    });
  }
}

import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-editproduct',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './editproduct.component.html',
  styleUrl: './editproduct.component.css'
})
export class EditproductComponent implements OnInit {

  editProductForm!: FormGroup;
  categories: any[] = [];
  message: any = null;
  imageUrl: any = null;
  productId: string = '';

  constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    // Get the product ID from the route parameters
    this.productId = this.route.snapshot.paramMap.get('productId') || '';

    this.editProductForm = this.fb.group({
      image: [null],
      categoryId: [''],
      name: [''],
      description: [''],
      price: [''],
      quantity: [''],
      discount: ['']
    });

    // Fetch all categories from the API
    this.apiService.getAllCategory().subscribe(res => {
      this.categories = res.categoryList;
    });

    // Fetch product details by ID from the API
    if (this.productId) {
      this.apiService.getProductById(this.productId).subscribe(res => {
        this.editProductForm.patchValue({
          categoryId: res.product.categoryId,
          name: res.product.name,
          description: res.product.description,
          price: res.product.price,
          quantity: res.product.quantity,
          discount: res.product.discount
        });
        this.imageUrl = res.product.imageUrl;
      });
    }
  }

  // Method to handle image file selection and preview
  handleImageChange(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files[0]) {
      const file = input.files[0];
      this.editProductForm.patchValue({ image: file });

      const reader = new FileReader();
      reader.onload = () => {
        this.imageUrl = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  // Method to handle form submission for product editing
  handleSubmit(): void {
    const formData = new FormData();
    const formValues = this.editProductForm.value;

    if (formValues.image) {
      formData.append('image', formValues.image);
    }
    formData.append('productId', this.productId);
    if (formValues.categoryId) {
      formData.append('categoryId', formValues.categoryId.toString());
    }
    if (formValues.name) {
      formData.append('name', formValues.name);
    }
    if (formValues.description) {
      formData.append('description', formValues.description);
    }
    if (formValues.price) {
      formData.append('price', formValues.price.toString());
    }
    if (formValues.quantity) {
      formData.append('quantity', formValues.quantity.toString());
    }
    if (formValues.discount) {
      formData.append('discount', formValues.discount.toString());
    }

    // Send the updated product details to the API
    this.apiService.updateProduct(formData).subscribe({
      next: (res) => {
        this.message = res.message;
        setTimeout(() => {
          this.message = '';
          this.router.navigate(['/admin/products']);
        }, 3000);
      },
      error: (error) => {
        console.log(error);
        this.message = "Unable to update a product";
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
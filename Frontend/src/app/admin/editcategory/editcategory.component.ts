import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-editcategory',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './editcategory.component.html',
  styleUrl: './editcategory.component.css'
})

export class EditcategoryComponent implements OnInit {
  categoryForm: FormGroup; // Variable to hold the form group for category editing
  message: any = null;
  categoryId: string = ''

  constructor(
    private apiService: ApiService,
    private router: Router,
    private fb: FormBuilder,
    private route: ActivatedRoute
  ) {

    this.categoryForm = this.fb.group({ // Initializing the form group with validation rules
      name: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    // Get the category ID from the route parameters
    this.categoryId = this.route.snapshot.paramMap.get("categoryId") || '';
    this.fetchCategoryById();
  }

  // Method to fetch category details by ID from the API
  fetchCategoryById(): void {
    if (this.categoryId) {
      this.apiService.getCategoryById(this.categoryId).subscribe({
        next: (response) => {
          this.categoryForm.patchValue({ name: response.category.name })
        },
        error: (error) => {
          console.log((error))
          this.message = "Unable to get categoty by id";
          setTimeout(() => {
            this.message = null;
          }, 3000)
        }
      })
    }
  }

  // Method to handle form submission for category editing
  handleSubmit(): void {
    if (this.categoryForm.valid) {
      this.apiService.updateCategory(this.categoryId, this.categoryForm.value).subscribe({
        next: (response) => {
          if (response.status === 200) {
            this.message = "Category successfully Updated"
            setTimeout(() => {
              this.message = null;
              this.router.navigate(['/admin/categories']);
            }, 3000)
          }
        },
        error: (error) => {
          console.log(error)
          this.message = "Unable to update Category";
        }
      })
    }
  }

}
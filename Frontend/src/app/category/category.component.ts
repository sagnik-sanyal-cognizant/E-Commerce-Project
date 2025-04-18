import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {

  constructor(
    private apiService:ApiService,
    private router:Router
  ){}

  error:any = null;
  categories: any[] = [];

  ngOnInit(): void {
      this.fetchCategories();
  }

  fetchCategories():void{ // Method to fetch categories from the API
    this.apiService.getAllCategory().subscribe({
      next: (response) =>{
        this.categories = response.categoryList || []
      },
      error: (err) =>{
        this.error = 'Unable to get categories!'
      }
    })
  }

  // Method to handle category click and navigate to the products page
  handleCategoryClick(categoryId: number): void{
    this.router.navigate(['/products', categoryId])
  }

}
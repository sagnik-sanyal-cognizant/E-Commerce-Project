import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { CartService } from '../service/cart.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { catchError, map, of } from 'rxjs';


@Component({
  selector: 'app-productdetails',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './productdetails.component.html',
  styleUrl: './productdetails.component.css'
})
export class ProductdetailsComponent implements OnInit {
  constructor(private apiService: ApiService,
    private cartService: CartService,
    private route: ActivatedRoute
  ) { }

  product: any = null;
  cartItem: any = null;
  productId: any = null;
  error: any = null;

  ngOnInit(): void {
    // Get the product ID from the route parameters
    this.productId = this.route.snapshot.paramMap.get('productId');
    this.fetchProduct();
  }

  async fetchProduct() { // Method to fetch product details from the API
    console.log("PRODUCT ID IS: " + this.productId)
    if (this.productId) {
      this.apiService.getProductById(this.productId).pipe(
        catchError(error => {
          console.log("ERROR IS: " + JSON.stringify(error))
          this.error = error.message;
          return of(null);
        }),
        map(response => response.product)
      ).subscribe(prodt => {
        if (prodt) {
          this.product = prodt
          this.cartItem = this.cartService.getCartItem(Number(this.productId));
        }
      });
    }
  }

  addToCart() { // Method to add the product to the cart
    window.alert("Added to Cart!");
    if (this.productId) {
      this.cartService.addItem(this.product);
      this.cartItem = this.cartService.getCartItem(this.product.id)
    }
  }

  incrementItem() { // Method to increment the quantity of the product in the cart
    if (this.product) {
      this.cartService.incrementItem(this.product.id);
      this.cartItem = this.cartService.getCartItem(this.product.id)
    }
  }

  decrementItem() { // Method to decrement the quantity of the product in the cart
    if (this.product && this.cartItem) {
      if (this.cartItem.quantity > 1) {
        this.cartService.decrementItem(this.product.id)
      } else {
        this.cartService.removeItem(this.product.id)
      }
      this.cartItem = this.cartService.getCartItem(this.product.id)
    }
  }

  // Method to convert base64 string to URL
  convertImageDataToUrl(imageData: string): string {
    const byteArray = Uint8Array.from(atob(imageData), c => c.charCodeAt(0));
    const blob = new Blob([byteArray], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
  }

}
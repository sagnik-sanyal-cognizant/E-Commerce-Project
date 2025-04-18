import { Component, Input } from '@angular/core';
import { CartService } from '../service/cart.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface Product {
  id: string;
  name: string;
  description: string;
  quantity: number;
  discount: number;
  price: number;
  imageData: string;
}

@Component({
  selector: 'app-productlist',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})
export class ProductlistComponent {

  constructor(
    private cartService: CartService,
    private router: Router
  ) {}

  // Input property to receive the list of products
  @Input() products: Product[] = [];


  // Method to add a product to the cart
  addToCart(product: Product): void {
    this.cartService.addItem(product);
    window.alert("Added to Cart!");
  }

  // Method to increment the quantity of a product in the cart
  incrementItem(product: Product): void {
    this.cartService.incrementItem(Number(product.id));
  }

  // Method to decrement the quantity of a product in the cart
  decrementItem(product: Product): void {
    this.cartService.decrementItem(Number(product.id));
  }

  // Method to check if a product is already in the cart
  inInCart(product: Product): boolean {
    return this.cartService.getCart().some(item => item.id === product.id);
  }

  // Method to get the cart item corresponding to a product
  getCartItem(product: Product): any {
    return this.cartService.getCart().find(item => item.id === product.id);
  }

  // Method to navigate to the product details page
  goToProductDetailsPage(productId: string): void {
    this.router.navigate(['/product', productId]);
  }

  // Method to convert base64 string to URL
  convertImageDataToUrl(imageData: string): string {
    const byteArray = Uint8Array.from(atob(imageData), c => c.charCodeAt(0));
    const blob = new Blob([byteArray], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
  }

}
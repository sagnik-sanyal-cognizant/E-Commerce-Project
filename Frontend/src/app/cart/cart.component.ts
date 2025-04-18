import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { CartService } from '../service/cart.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {

  constructor(
    private apiService:ApiService,
    private cartService:CartService,
    private router:Router
  ) {}

  cart:any[] = [];
  message: any = null;
  totalPrice:number = 0;

  ngOnInit(): void {
      this.loadCart();
  }

  loadCart():void{ // Method to load cart items from the CartService
    this.cart = this.cartService.getCart();
    this.calculateTotalPrice()
  }

  calculateTotalPrice(): void { // Method to calculate the total price of items in the cart
    this.totalPrice = this.cart.reduce((total, item) => {
      return total + (item.price - item.discount) * item.quantity;
    }, 0);
  }


  incrementItem(itemId: number):void{ // Method to increment the quantity of a cart item
    this.cartService.incrementItem(itemId)
    this.loadCart();
  }

  decrementItem(itemId: number):void{ // Method to decrement the quantity of a cart item
    this.cartService.decrementItem(itemId)
    this.loadCart();
  }

  removeItem(itemId: number):void{ // Method to remove an item from the cart
    this.cartService.removeItem(itemId)
    this.loadCart();
  }
  clearCart():void{ // Method to remove an item from the cart
    this.cartService.clearCart()
    this.loadCart();
  }

  handleCheckOut():void{ // Method to handle the checkout process
    if (!this.apiService.isAuthenticated()) { // Check if the user is authenticated before proceeding
      this.message = "you need to login before you can place an order"
      setTimeout(()=>{
        this.message = null
        this.router.navigate(['/login'])
      }, 3000)
      return;
    }

    const orderItems = this.cart.map(item =>({ // Prepare the order items for the API request
      productId: item.id,
      quantity: item.quantity
    }));

    const orderRequest = { // Create the order request object
      totalPrice: this.totalPrice,
      items: orderItems
    }

    this.apiService.createOrder(orderRequest).subscribe({ // Send the order request to the API
      next:(response)=>{
        this.message = (response.message)
        if (response.status === 200) {
          this.cartService.clearCart();
          this.loadCart();
        }
      },
      error:(error)=>{
        console.log(error)
        this.message = "Unable to place order!"
      }
    })

  }

  // Method to convert base64 string to URL
  convertImageDataToUrl(imageData: string): string {
    const byteArray = Uint8Array.from(atob(imageData), c => c.charCodeAt(0));
    const blob = new Blob([byteArray], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
  }

}
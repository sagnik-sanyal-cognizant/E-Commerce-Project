import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  authStatuschanged = new EventEmitter<void>();
  private static BASE_URL = 'http://localhost:2022';

  constructor(
    private http: HttpClient
  ) { }

  private getHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  // AUTHENTICATION

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !!token;
  }

  isAdmin(): boolean {
    const role = localStorage.getItem('role');
    return role === 'ADMIN';
  }

  // LOGIN & REGISTRATION API

  registerUser(registration: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/auth/register`, registration);
  }

  loginUser(loginDetails: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/auth/login`, loginDetails);
  }

  getLoggedInUserInfo(): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/user/my-info`, {
      headers: this.getHeader()
    });
  }

  getAllUsers(): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/user/get-all`, {
      headers: this.getHeader()
    });
  }

  // PRODUCTS API

  addProduct(formData: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/product/create`, formData, {
      headers: this.getHeader()
    });
  }

  updateProduct(formData: any): Observable<any> {
    return this.http.put(`${ApiService.BASE_URL}/product/update`, formData, {
      headers: this.getHeader()
    });
  }

  getAllProducts(): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/product/get-all`);
  }

  searchProducts(searchValue: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/product/search`, {
      params: { searchValue }
    });
  }

  getAllProductsByCategotyId(categoryId: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/product/get-by-category-id/${categoryId}`);
  }

  getProductById(productId: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/product/get-by-product-id/${productId}`);
  }

  deletProduct(productId: string): Observable<any> {
    return this.http.delete(`${ApiService.BASE_URL}/product/delete/${productId}`, {
      headers: this.getHeader()
    });
  }

  // CATEGOTY API

  createCategory(body: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/category/create`, body, {
      headers: this.getHeader()
    });
  }

  getAllCategory(): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/category/get-all`);
  }

  getCategoryById(categoryId: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/category/get-category-by-id/${categoryId}`);
  }

  updateCategory(categoryId: string, body: any): Observable<any> {
    return this.http.put(`${ApiService.BASE_URL}/category/update/${categoryId}`, body, {
      headers: this.getHeader()
    });
  }

  deleteCategory(categoryId: string): Observable<any> {
    return this.http.delete(`${ApiService.BASE_URL}/category/delete/${categoryId}`, {
      headers: this.getHeader()
    });
  }

  // ORDER API

  createOrder(body: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/order/create`, body, {
      headers: this.getHeader()
    });
  }

  getAllOrders(): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/order/filter`, {
      headers: this.getHeader()
    });
  }

  getOrderItemById(itemId: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/order/filter`, {
      headers: this.getHeader(),
      params: { itemId }
    });
  }

  getAllOrderItemsByStatus(status: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/order/filter`, {
      headers: this.getHeader(),
      params: { status }
    });
  }

  updateOrderItemStatus(orderItemId: string, status: string): Observable<any> {
    return this.http.put(`${ApiService.BASE_URL}/order/update-item-status/${orderItemId}`, {}, {
      headers: this.getHeader(),
      params: { status }
    });
  }

  // ADDRESS API

  saveAddress(body: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/address/save`, body, {
      headers: this.getHeader()
    });
  }

  getAddressByUserId(userId: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/address/user/${userId}`, {
      headers: this.getHeader()
    });
  }

  // CART API

  addToCart(productId: string): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/cart/add`, null, {
      headers: this.getHeader(),
      params: { productId }
    });
  }

  updateCartItemByQuantity(cartItemId: string, productId: string): Observable<any> {
    return this.http.put(`${ApiService.BASE_URL}/cart/update/add/${cartItemId}/${productId}`, null, {
      headers: this.getHeader()
    });
  }

  removeCartItemByQuantity(cartItemId: string, productId: string): Observable<any> {
    return this.http.put(`${ApiService.BASE_URL}/cart/update/remove/${cartItemId}/${productId}`, null, {
      headers: this.getHeader()
    });
  }

  removeCartItem(cartItemId: string): Observable<any> {
    return this.http.delete(`${ApiService.BASE_URL}/cart/delete/${cartItemId}`, {
      headers: this.getHeader()
    });
  }

  getCartByUser(userId: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/cart/user/${userId}`, {
      headers: this.getHeader()
    });
  }

  searchCartItemsByProductName(productName: string): Observable<any> {
    return this.http.get(`${ApiService.BASE_URL}/cart/search`, {
      headers: this.getHeader(),
      params: { productName }
    });
  }

}
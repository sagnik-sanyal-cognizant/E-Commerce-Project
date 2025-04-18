import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../service/api.service';
import { Router, RouterLink } from '@angular/router';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(
    private readonly apiService: ApiService,
    private router: Router
  ) { }
  formData: any = {
    email: '',
    password: ''
  }
  message: any = null; // Variable to hold messages for user feedback

  async handleSubmit() { // Method to handle form submission for login
    if (!this.formData.email ||  !this.formData.password) {
      this.showMessage("Email and Password are required")
      return;
    }

    try { // Attempt to login the user using the API
      const response: any = await firstValueFrom(this.apiService.loginUser(this.formData));
      if (response.status === 200) {
        this.showMessage('User Successfully logged in');
        localStorage.setItem('token', response.token)
        localStorage.setItem('role', response.role)
        this.apiService.authStatuschanged.emit();
        this.router.navigate(['/profile'])
      }
    } catch (error: any) {
        console.log(error)
        this.showMessage('Unable to login');
    }
  }

  // Method to display messages to the user
  showMessage(message: string) {
    this.message = message;
    setTimeout(() => {
      this.message == null
    }, 3000);
  }
}
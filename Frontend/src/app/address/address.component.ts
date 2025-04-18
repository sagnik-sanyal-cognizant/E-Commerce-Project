import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-address',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './address.component.html',
  styleUrl: './address.component.css'
})
export class AddressComponent implements OnInit {

  addressForm: FormGroup;
  error: any = null;
  isEditMode: boolean;

  constructor(
    private apiService: ApiService,
    private fb: FormBuilder,
    private router: Router
  ) {

    // Determine if the component is in edit mode based on the URL
    this.isEditMode = this.router.url.includes('edit-address');
    this.addressForm = this.fb.group({});
  }

  ngOnInit(): void { // Define the form controls and their validation
    this.addressForm = this.fb.group({
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zipCode: ['', Validators.pattern('^[0-9]*$')],
      country: ['', Validators.required]
    });

    if (this.isEditMode) { // If in edit mode, fetch the user's address information
      this.fetchUserAddressInfo();
    }
  }

  fetchUserAddressInfo(): void { // Get the address from the history state if available
    const address = history.state.address;
    if (address) {
      this.addressForm.patchValue(address);
    } else { // Fetch the logged-in user's address from the
      this.apiService.getLoggedInUserInfo().subscribe({
        next: (response) => {
          if (response.user.address) {
            this.addressForm.patchValue(response.user.address);
          }
        },
        error: (error) => {
          console.log(error);
          this.showError(error?.error?.message || "Unable to get user address");
        }
      });
    }
  }

  handleSubmit(): void { // Check if the form is valid before submitting
    if (this.addressForm.invalid) {
      this.showError("Please fill in all fields");
      return;
    }

    // Save the address using the API service
    this.apiService.saveAddress(this.addressForm.value).subscribe({
      next: (response) => {
        this.router.navigate(['/profile']);
      },
      error: (error) => {
        console.log(error);
        this.showError(error?.error?.message || "Unable to save user address");
      }
    });
  }

  showError(message: string): void { // Display the error message and clear it after 3 seconds
    this.error = message;
    setTimeout(() => {
      this.error = null;
    }, 3000);
  }
}

package com.cts.ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
	private String city;

	@NotBlank(message = "Street is required")
    @Size(min = 2, max = 100, message = "Street must be between 2 and 100 characters")
	private String street;

	@NotBlank(message = "State is required")
    @Size(min = 2, max = 100, message = "State must be between 2 and 100 characters")
	private String state;

	@NotBlank(message = "Zip code is required")
    @Size(min = 6, max = 6, message = "Zip code must be 6 characters")
	private String zipCode;

	@NotBlank(message = "Country is required")
    @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
	private String country;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "created_at")
	private final LocalDateTime createdAt = LocalDateTime.now();
}

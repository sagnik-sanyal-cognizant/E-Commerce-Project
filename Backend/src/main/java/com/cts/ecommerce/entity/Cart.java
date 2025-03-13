package com.cts.ecommerce.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart 
{
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    //only one user is responsible for one cart
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //total price of the cart
    private BigDecimal totalPrice;

    //one cart have list of cartItems
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItemList;
}

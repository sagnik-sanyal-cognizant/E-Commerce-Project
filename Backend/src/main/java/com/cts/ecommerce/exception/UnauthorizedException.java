package com.cts.ecommerce.exception;

public class UnauthorizedException extends RuntimeException 
{
    public UnauthorizedException(String message) 
    {
        super(message);
    }
}

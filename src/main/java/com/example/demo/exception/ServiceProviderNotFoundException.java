package com.example.demo.exception;

public class ServiceProviderNotFoundException extends RuntimeException {
	public ServiceProviderNotFoundException(String message) {
		super(message);
	}
}

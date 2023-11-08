package com.example.demo.controller.exception;

public class SubcontractorNotFoundException extends RuntimeException {
	public SubcontractorNotFoundException(String message) {
		super(message);
	}
}

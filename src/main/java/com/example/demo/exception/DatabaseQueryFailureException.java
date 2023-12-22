package com.example.demo.exception;

public class DatabaseQueryFailureException extends Exception {
	public DatabaseQueryFailureException(String message) {
		super(message);
	}
}

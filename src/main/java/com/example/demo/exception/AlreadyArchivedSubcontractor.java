package com.example.demo.exception;

public class AlreadyArchivedSubcontractor extends RuntimeException {
	public AlreadyArchivedSubcontractor(String message) {
		super(message);
	}
}

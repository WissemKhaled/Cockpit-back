package com.example.demo.service;

import com.example.demo.entity.Subcontractor;

public interface SubcontractorService {
	int saveSubcontractor(Subcontractor subcontractor);

	void updateSubcontractor(Subcontractor subcontractor);
	
	Subcontractor getSubcontractorById(int Id);
}

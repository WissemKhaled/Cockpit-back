package com.example.demo.service;

import com.example.demo.entity.Subcontractor;

public interface SubcontractorService {

	Subcontractor getSubcontractorWithStatus(int id);

	int saveSubcontractor(Subcontractor subcontractor);

	int updateSubcontractor(Subcontractor subcontractor);

	void archiveSubcontractor(Subcontractor subcontractortoArchive);

}

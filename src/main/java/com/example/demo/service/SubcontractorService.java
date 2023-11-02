package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;

public interface SubcontractorService {

	List<SubcontractorDto> getAllSubcontractor(String nameColonne, String sorting, int pageSize, int page);

	int getNumbersOfPages(int pageSize);

	Subcontractor getSubcontractorWithStatus(int id);

	int saveSubcontractor(Subcontractor subcontractor);

	int updateSubcontractor(Subcontractor subcontractor);

}

package com.example.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;

public interface SubcontractorService {
	
	int saveSubcontractor(Subcontractor subcontractor);

	void updateSubcontractor(Subcontractor subcontractor);
	
	Subcontractor getSubcontractorById(int Id);
	
	List<SubcontractorDto> getAllSubcontractor(String nameColonne , String sorting , int pageSize, int page );
	
	int getNumbersOfPages(int pageSize);
}

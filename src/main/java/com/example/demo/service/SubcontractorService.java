package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;

public interface SubcontractorService {

	List<SubcontractorDto> getAllSubcontractor(String nameColonne, String sorting, int pageSize, int page);

	List<SubcontractorDto> getAllSubcontractorWhitStatus(String nameColonne, String sorting, int pageSize, int page,
			int statusId);

	List<Status> getAllStatus();

	int getNumbersOfPages();

	Integer getNumbersOfSubContractor();

	Integer countTotalItemWhitStatus(Integer statusId);
	
	Subcontractor getSubcontractorWithStatus(int sId);

	int saveSubcontractor(Subcontractor subcontractor);

	int updateSubcontractor(Subcontractor subcontractor);

	int archiveSubcontractor(Subcontractor subcontractortoArchive);

	boolean checkIfSubcontractorExist(int sId);

	int checkIfSubcontractorExistBySName(String sName);

	int checkIfSubcontractorExistBySEmail(String sEmail);

	void handleSubcontractorSave(SubcontractorDto subcontractorDto);

	void handleSubcontractorUpdate(SubcontractorDto subcontractorDto);
	
	Subcontractor getSubcontractorBySName(String sName);

}

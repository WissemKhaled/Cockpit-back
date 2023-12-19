package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StatusDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;

public interface SubcontractorService {

	List<SubcontractorDto> getAllSubcontractors(String nameColonne, String sorting, int pageSize, int page);

	List<SubcontractorDto> getAllSubcontractorWhitStatus(String nameColonne, String sorting, int pageSize, int page,
			int statusId);

	List<StatusDto> getAllStatus();

	List<SubcontractorDto> getAllSubcontractors();

	int getNumbersOfPages();

	Integer getNumberOfAllSubcontractors();

	Integer countTotalItemWhitStatus(Integer statusId);

	SubcontractorDto getSubcontractorWithStatus(int sId);

	int saveSubcontractor(SubcontractorDto subcontractorDtoToUpdate);

	int updateSubcontractor(SubcontractorDto subcontractorDtoToSave);

	int archiveSubcontractor(SubcontractorDto subcontractortoArchive);

	boolean checkIfSubcontractorExist(int sId);

	int checkIfSubcontractorExistBySName(String sName);

	int checkIfSubcontractorExistBySEmail(String sEmail);

	void handleSubcontractorSave(SubcontractorDto subcontractorDto);

	void handleSubcontractorUpdate(SubcontractorDto subcontractorDto);

	Subcontractor getSubcontractorBySName(String sName);

}

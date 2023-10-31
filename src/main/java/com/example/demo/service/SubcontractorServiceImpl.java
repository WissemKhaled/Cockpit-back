package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.entity.Subcontractor;
import com.example.demo.mappers.SubcontractorDtoMapper;
import com.example.demo.mappers.SubcontractorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubcontractorServiceImpl implements SubcontractorService {

	private SubcontractorMapper subcontractorMapper;
	
	private SubcontractorDtoMapper dtoMapper;

	@Override
	public int saveSubcontractor(Subcontractor subcontractor) {
		subcontractorMapper.insertSubcontractor(subcontractor);
		return subcontractor.getSId();
	}

	@Override
	public void updateSubcontractor(Subcontractor subcontractor) {
		subcontractorMapper.updateSubcontractor(subcontractor);
	}

	@Override
	public Subcontractor getSubcontractorById(int Id) {
		return subcontractorMapper.findSubcontractorById(Id);

	}

	//methode qui retourne tous les sousTraitants en DTO
	@Override
	public List<SubcontractorDto> getAllSubcontractor(int page , int pageSize) {
		
		int totalItems = subcontractorMapper.countTotalItems();
	    int nbPages  = (int) Math.ceil((double) totalItems / pageSize);
		
	    System.out.println(nbPages);
	    
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize ;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractors( offset , pageSize);
		
		for (Subcontractor subcontractor : subContarcList) {
		}

		if (!subContarcList.isEmpty()) {
			
			for(Subcontractor subcontractor : subContarcList) {
				
				subcontractorDtosList.add(dtoMapper.subcontractorToDto(subcontractor));
			}
			
			return subcontractorDtosList;
			
		}
		else throw new RuntimeException("Il n'y a pas de sousTraitans");
				
	}
	
	public int getNumbersOfPages(int pageSize) {
		System.err.println(pageSize);
		int totalItems = subcontractorMapper.countTotalItems();
	    int nbPages  = (int) Math.ceil((double) totalItems / pageSize);
		
	    return nbPages ;
	}

}

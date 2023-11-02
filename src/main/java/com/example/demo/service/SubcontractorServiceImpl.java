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

	//debut hamza : methode qui retourne tous les sousTraitants en DTO et qui prend en parametre 
	//pour le tri le nom de la colonne et le type de tri
	//et pour la pagination le nombre déelement a aficcher et la page en question 
	@Override
	public List<SubcontractorDto> getAllSubcontractor(String nameColonne , String sorting ,int page , int pageSize) {
		
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize ;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractors(nameColonne, sorting, offset , pageSize);
	
		
		if (!subContarcList.isEmpty()) {
			
			for(Subcontractor subcontractor : subContarcList) {
				
				subcontractorDtosList.add(dtoMapper.subcontractorToDto(subcontractor));
			}
			
			return subcontractorDtosList;
			
		}
		else throw new RuntimeException("Il n'y a pas de sousTraitans");
				
	}
	//fin
	
	
	//debut hamza : ce code permet de retoruner le nombre max de page qu'il y a  
	public int getNumbersOfPages(int pageSize) {
		System.err.println(pageSize);
		int totalItems = subcontractorMapper.countTotalItems();
	    int nbPages  = (int) Math.ceil((double) totalItems / pageSize);
		
	    return nbPages ;
	}
	//fin

}

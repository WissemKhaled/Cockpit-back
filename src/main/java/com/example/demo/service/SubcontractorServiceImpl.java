package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.controller.exception.SubcontractorNotFoundException;
import com.example.demo.entity.Subcontractor;
import com.example.demo.mappers.SubcontractorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubcontractorServiceImpl implements SubcontractorService {

	private final SubcontractorMapper subcontractorMapper;

	@Override
	public int saveSubcontractor(Subcontractor subcontractor) {
		subcontractorMapper.insertSubcontractor(subcontractor);
		return subcontractor.getSId();
	}

	@Override
	public Subcontractor getSubcontractorWithStatus(int sId) {
        Subcontractor subcontractor = subcontractorMapper.getSubcontractorWithStatus(sId);
        if (subcontractor == null) {
            throw new SubcontractorNotFoundException("le sous-traitant avec l'id: "+ sId +" n'existe pas!!");
        }
		return subcontractor;
	}

	@Override
	public void updateSubcontractor(Subcontractor subcontractor) {
		subcontractorMapper.updateSubcontractor(subcontractor);
	}
	
}

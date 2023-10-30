package com.example.demo.service;

import org.springframework.stereotype.Service;

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
	public Subcontractor getSubcontractorById(int Id) {
		return subcontractorMapper.findSubcontractorById(Id);

	}

	@Override
	public Subcontractor getSubcontractorWithStatus(int sId) {
		return subcontractorMapper.getSubcontractorWithStatus(sId);
	}

	@Override
	public void updateSubcontractor(Subcontractor subcontractor) {
		subcontractorMapper.updateSubcontractor(subcontractor);
	}
}

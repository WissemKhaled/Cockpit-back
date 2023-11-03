package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.SubcontractorNotFoundException;
import com.example.demo.mappers.SubcontractorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubcontractorServiceImpl implements SubcontractorService {

	private final SubcontractorMapper subcontractorMapper;

	@Override
	public int saveSubcontractor(Subcontractor subcontractor) {
		int isSubcontractorInserted = subcontractorMapper.insertSubcontractor(subcontractor);
		if (isSubcontractorInserted == 0) {
			return isSubcontractorInserted;
		}
		// remarque qu'on persiste le sous-traitant, on génere l'id
		// automatiquement et comme ça on peut retourner le correct sans
		// prendre en cpnsidération l'id saisi par l'utilisateur
		// (subcontractDto)
		return subcontractor.getSId();
	}

	@Override
	public Subcontractor getSubcontractorWithStatus(int sId) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(sId);
		if (subcontractor == null) {
			throw new SubcontractorNotFoundException("le sous-traitant avec l'id: " + sId + " n'existe pas!!");
		}
		return subcontractor;
	}

	@Override
	public int updateSubcontractor(Subcontractor subcontractor) {
		int isSubcontractorUpdated = subcontractorMapper.updateSubcontractor(subcontractor);
		return isSubcontractorUpdated;
	}

	@Override
	public int archiveSubcontractor(Subcontractor subcontractortoArchive) {
		return subcontractorMapper.archive(subcontractortoArchive);
	}

}

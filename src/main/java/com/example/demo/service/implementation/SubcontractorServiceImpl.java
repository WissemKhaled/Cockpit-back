package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SubcontractorDto;
import com.example.demo.dto.mapper.SubcontractorDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.SubcontractorService;

@Service
public class SubcontractorServiceImpl implements SubcontractorService {
	private final SubcontractorDtoMapper subcontractorDtoMapper;
	private final SubcontractorMapper subcontractorMapper;
	private final StatusMapper statusMapper;
	private final ServiceProviderMapper serviceProviderMapper;

	public SubcontractorServiceImpl(SubcontractorDtoMapper subcontractorDtoMapper,
			SubcontractorMapper subcontractorMapper, StatusMapper statusMapper,
			ServiceProviderMapper serviceProviderMapper) {
		this.subcontractorDtoMapper = subcontractorDtoMapper;
		this.subcontractorMapper = subcontractorMapper;
		this.statusMapper = statusMapper;
		this.serviceProviderMapper = serviceProviderMapper;
	}

	@Override
	public Subcontractor getSubcontractorWithStatus(int sId) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(sId);
		if (subcontractor == null) {
			throw new EntityNotFoundException("le sous-traitant avec l'id: " + sId + " n'existe pas!!");
		}
		return subcontractor;
	}

	@Override
	public int saveSubcontractor(Subcontractor subcontractor) {
		subcontractor.setSCreationDate(LocalDateTime.now());
		int isSubcontractorInserted = subcontractorMapper.insertSubcontractor(subcontractor);
		if (isSubcontractorInserted == 0) {
			return isSubcontractorInserted;
		}
		// remarque qu'on persiste le sous-traitant, on génere l'id automatiquement et
		// comme ça on peut retourner le correct sans prendre en cpnsidération l'id
		// saisi par l'utilisateur (subcontractDto)
		return subcontractor.getSId();
	}

	@Override
	public int updateSubcontractor(Subcontractor subcontractor) {
		subcontractor.setSLastUpdateDate(LocalDateTime.now());
		return subcontractorMapper.updateSubcontractor(subcontractor);
	}

	@Override
	public int archiveSubcontractor(Subcontractor subcontractorToArchive) {
		int isArchivedSubcontractor = subcontractorMapper.archiveSubcontractor(subcontractorToArchive);
		List<ServiceProvider> foundedServiceProvidersBySubcontractorId = serviceProviderMapper
				.findServiceProvidersBySubcontractorId(subcontractorToArchive.getSId());
		if (!foundedServiceProvidersBySubcontractorId.isEmpty()) {
			for (ServiceProvider serviceProvider : foundedServiceProvidersBySubcontractorId) {
				serviceProviderMapper.archiveServiceProvider(serviceProvider);
			}
		}
		return isArchivedSubcontractor;
	}

	// debut hamza : ce code permet de retoruner le nombre max de page qu'il y a
	public Integer getNumbersOfSubContractor() {
		return subcontractorMapper.countTotalItems();
	}

	@Override
	public Integer countTotalItemWhitStatus(Integer statusId) {

		return subcontractorMapper.countTotalItemsWithStatus(statusId);

	}

	// methode qui retourne tous les sousTraitants en DTO et qui prend en parametre
	// pour le tri le nom de la colonne et le type de tri et pour la pagination le
	// nombre déelement a aficcher et la page en question
	@Override
	public List<SubcontractorDto> getAllSubcontractor(String nameColonne, String sorting, int page, int pageSize) {
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractors(nameColonne, sorting, offset,
				pageSize);
		if (!subContarcList.isEmpty()) {
			for (Subcontractor subcontractor : subContarcList) {
				subcontractorDtosList.add(subcontractorDtoMapper.subcontractorToDto(subcontractor));
			}
			return subcontractorDtosList;
		} else
			throw new RuntimeException("Il n'y a pas de sous-traitans");
	}

	// ce code permet de retoruner le nombre max de page qu'il y a
	public int getNumbersOfPages() {
		return subcontractorMapper.countTotalItems();
	}

	@Override
	public List<Status> getAllStatus() {
		return statusMapper.getAllStatus();
	}

	@Override
	public List<SubcontractorDto> getAllSubcontractorWhitStatus(String nameColonne, String sorting, int pageSize,
			int page, int statusId) {
		List<SubcontractorDto> subcontractorDtosList = new ArrayList<>();
		int offset = (page - 1) * pageSize;
		List<Subcontractor> subContarcList = subcontractorMapper.getAllSubcontractorsWhitStatus(nameColonne, sorting,
				offset, pageSize, statusId);

		if (!subContarcList.isEmpty()) {
			for (Subcontractor subcontractor : subContarcList) {
				subcontractorDtosList.add(subcontractorDtoMapper.subcontractorToDto(subcontractor));
			}
			return subcontractorDtosList;
		} else
			throw new RuntimeException("Il n'y a pas de sous-traitans");
	}

	@Override
	public boolean checkIfSubcontractorExist(int sId) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusById(sId);
		if (subcontractor == null) {
			return false;
		}
		return true;
	}

	@Override
	public int checkIfSubcontractorExistBySName(String sName) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySName(sName.toUpperCase());
		if (subcontractor == null) {
			return 0;
		}
		return subcontractor.getSId();
	}

	@Override
	public int checkIfSubcontractorExistBySEmail(String sEmail) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySEmail(sEmail);
		if (subcontractor == null) {
			return 0;
		}
		return subcontractor.getSId();
	}

	@Override
	public void handleSubcontractorSave(SubcontractorDto subcontractorDto) {
		int isSubcontractorExistBysName = checkIfSubcontractorExistBySName(subcontractorDto.getSName());
		if (isSubcontractorExistBysName != 0) {
			throw new EntityDuplicateDataException("le nom saisi existe déjà");
		}
		int isSubcontractorExistBysEmail = checkIfSubcontractorExistBySEmail(subcontractorDto.getSEmail());
		if (isSubcontractorExistBysEmail != 0) {
			throw new EntityDuplicateDataException("l'émail saisi existe déjà");
		}
	}

	@Override
	public void handleSubcontractorUpdate(SubcontractorDto subcontractorDto) {
		int isSubcontractorExistBysName = checkIfSubcontractorExistBySName(subcontractorDto.getSName());
		if (isSubcontractorExistBysName != 0 && subcontractorDto.getSId() != isSubcontractorExistBysName) {
			throw new EntityDuplicateDataException("le nom saisi existe déjà");
		}
		int isSubcontractorExistBysEmail = checkIfSubcontractorExistBySEmail(subcontractorDto.getSEmail());
		if (isSubcontractorExistBysEmail != 0 && subcontractorDto.getSId() != isSubcontractorExistBysEmail) {
			throw new EntityDuplicateDataException("l'émail saisi existe déjà");
		}
	}

	@Override
	public Subcontractor getSubcontractorBySName(String sName) {
		Subcontractor foundSubcontractorBySName = subcontractorMapper.findSubcontractorWithStatusBySName(sName);
		if (foundSubcontractorBySName == null) {
			throw new EntityNotFoundException("le sous-traitant avec le nom: " + sName + " n'existe pas!!");
		}
		return foundSubcontractorBySName;
	}

	@Override
	public List<Subcontractor> getAllSubcontractors() {
		List<Subcontractor> subcontractors = subcontractorMapper.findAllSubcontractors();
		if (subcontractors.isEmpty())
			throw new EntityNotFoundException("Il n'y a pas de sous-traiatns enregistrés");
		return subcontractors;
	}

}

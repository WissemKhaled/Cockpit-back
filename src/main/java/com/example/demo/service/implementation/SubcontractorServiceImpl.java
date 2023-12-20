package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StatusDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.dto.mapper.StatusDtoMapper;
import com.example.demo.dto.mapper.SubcontractorDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.SubcontractorService;

@Service
public class SubcontractorServiceImpl implements SubcontractorService {
	private final SubcontractorDtoMapper subcontractorDtoMapper;
	private final SubcontractorMapper subcontractorMapper;
	private final StatusMapper statusMapper;
	private final StatusDtoMapper statusDtoMapper;
	private final ServiceProviderMapper serviceProviderMapper;

	public SubcontractorServiceImpl(SubcontractorDtoMapper subcontractorDtoMapper,
			SubcontractorMapper subcontractorMapper, StatusMapper statusMapper, StatusDtoMapper statusDtoMapper,
			ServiceProviderMapper serviceProviderMapper) {
		this.subcontractorDtoMapper = subcontractorDtoMapper;
		this.subcontractorMapper = subcontractorMapper;
		this.statusMapper = statusMapper;
		this.statusDtoMapper = statusDtoMapper;
		this.serviceProviderMapper = serviceProviderMapper;
	}

	@Override
	public SubcontractorDto getSubcontractorWithStatus(int sId) {
		SubcontractorDto foundedSubcontractor = subcontractorDtoMapper.subcontractorToDto(subcontractorMapper.findSubcontractorWithStatusById(sId));
		
		if (foundedSubcontractor == null) {
			throw new EntityNotFoundException("le sous-traitant avec l'id: " + sId + " n'existe pas!!");
		}
		return foundedSubcontractor;
	}

	@Override
	public int saveSubcontractor(SubcontractorDto subcontractorDto) {
		Subcontractor subcontractorDtoForSaving = subcontractorDtoMapper.dtoToSubcontractor(subcontractorDto);
		subcontractorDtoForSaving.setSCreationDate(LocalDateTime.now());
		int isSubcontractorInserted = subcontractorMapper.insertSubcontractor(subcontractorDtoForSaving);
		if (isSubcontractorInserted == 0) {
			return isSubcontractorInserted;
		}
		// remarque qu'on persiste le sous-traitant, on génere l'id automatiquement et
		// comme ça on peut retourner le correct sans prendre en cpnsidération l'id
		// saisi par l'utilisateur (subcontractDto)
		return subcontractorDtoForSaving.getSId();
	}

	@Override
	public int updateSubcontractor(SubcontractorDto subcontractorDto) {
		Subcontractor subcontractorDtoForUpdated = subcontractorDtoMapper.dtoToSubcontractor(subcontractorDto);
		subcontractorDtoForUpdated.setSLastUpdateDate(LocalDateTime.now());
		return subcontractorMapper.updateSubcontractor(subcontractorDtoForUpdated);
	}

	@Override
	public int archiveSubcontractor(SubcontractorDto subcontractorDtoToArchive) {
		Subcontractor subcontractorToArchive = subcontractorDtoMapper.dtoToSubcontractor(subcontractorDtoToArchive);
		int isArchivedSubcontractor = subcontractorMapper.archiveSubcontractor(subcontractorToArchive);
		List<ServiceProvider> foundedServiceProvidersBySubcontractorId = serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorDtoToArchive.getSId());
		if (!foundedServiceProvidersBySubcontractorId.isEmpty()) {
			for (ServiceProvider serviceProvider : foundedServiceProvidersBySubcontractorId) {
				serviceProviderMapper.archiveServiceProvider(serviceProvider);
			}
		}
		return isArchivedSubcontractor;
	}

	public Integer getNumberOfAllSubcontractors() {
		Integer numberOfFoundSubcontractors = subcontractorMapper.countTotalItems();
		if (numberOfFoundSubcontractors == 0) {
			throw new EntityNotFoundException("il n'y a pas de sous-traiatant trouvé");
		}
		return numberOfFoundSubcontractors;
	}

	@Override
	public Integer countTotalItemWhitStatus(Integer statusId) {
		Integer numberOfFoundSubcontractorsWithStatus = subcontractorMapper.countTotalItemsWithStatus(statusId);
		if (numberOfFoundSubcontractorsWithStatus == 0) {
			throw new EntityNotFoundException("il n'y a pas de sous-traiatant trouvé");
		}
		return numberOfFoundSubcontractorsWithStatus;

	}

	// methode qui retourne tous les sousTraitants en DTO et qui prend en parametre
	// pour le tri le nom de la colonne et le type de tri et pour la pagination le
	// nombre déelement a aficcher et la page en question
	@Override
	public List<SubcontractorDto> getAllSubcontractors(String nameColonne, String sorting, int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		List<SubcontractorDto> foundedSubcontractors = subcontractorMapper.getAllSubcontractors(nameColonne, sorting, offset,pageSize).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
		if (foundedSubcontractors.isEmpty()) {
			throw new EntityNotFoundException("Il n'y a pas de sous-traitans");
		}
		return foundedSubcontractors;
	}

	// ce code permet de retoruner le nombre max de page qu'il y a
	public int getNumbersOfPages() {
		return subcontractorMapper.countTotalItems();
	}

	@Override
	public List<StatusDto> getAllStatus() {
		List<StatusDto> foundedStatus = statusMapper.getAllStatus().stream().map(statusDtoMapper::statusToDto).toList();
		if (foundedStatus.isEmpty()) {
			throw new EntityNotFoundException("Il n'y a pas de status enregistré");
		}
		return foundedStatus;
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
	public List<SubcontractorDto> getAllSubcontractors() {
		List<SubcontractorDto> subcontractors = subcontractorMapper.findAllSubcontractors().stream()
				.map(subcontractorDtoMapper::subcontractorToDto).toList();
		if (subcontractors.isEmpty())
			throw new EntityNotFoundException("Il n'y a pas de sous-traiatns enregistrés");
		return subcontractors;
	}

	@Override
	public List<SubcontractorDto> getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms,
			int pageNumber, int pageSize, int statusId, String searchAttribute) throws GeneralException {
	    // Calcul de l'offset pour la pagination
	    int offset = (pageNumber - 1) * pageSize;

	    // Vérification de l'attribut de recherche et récupération des prestataires en conséquence
	    if (searchAttribute.equals("name")) {
	        if (statusId == 0) {
	            return subcontractorMapper.findAllSubcontractorsBySubcontractorName(searchTerms, offset, pageSize).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        } else {
	            return subcontractorMapper.findAllSubcontractorsBySubcontractorNameAndFiltredStatus(searchTerms, offset, pageSize, statusId).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        }
	    } else if (searchAttribute.equals("email")) {
	        if (statusId == 0) {
	            return subcontractorMapper.findAllSubcontractorsBySubcontractorEmail(searchTerms, offset, pageSize).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        } else {
	            return subcontractorMapper.findAllSubcontractorsBySubcontractorEmailAndFiltredStatus(searchTerms, offset, pageSize, statusId).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", searchAttribute));
	    }
	}

	@Override
	public Integer getNumberOfSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String searchAttribute) throws GeneralException {
	    if (searchAttribute.equals("name")) {
	        // Vérification de l'attribut de recherche et récupération du nombre de prestataires de services en conséquence
	        if (statusId == 0) {
	            return subcontractorMapper.findNumberOfAllSubcontractorsBySubcontractorName(searchTerms);
	        } else {
	            return subcontractorMapper.findNumberOfAllSubcontractorsBySubcontractorNameAndFiltredByStatus(searchTerms,statusId);
	        }
	    } else if (searchAttribute.equals("email")) {
	        if (statusId == 0) {
	            return subcontractorMapper.findNumberOfAllSubcontractorsBySubcontractorEmail(searchTerms);
	        } else {
	            return subcontractorMapper.findNumberOfAllSubcontractorsBySubcontractorEmailAndFiltredByStatus(searchTerms,statusId);
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", searchAttribute));
	    }
	}

}

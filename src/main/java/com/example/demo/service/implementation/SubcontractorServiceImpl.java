package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GstStatusModelSubcontractorDTO;
import com.example.demo.dto.StatusDto;
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.dto.mapper.GstStatusModelSubcontractorDtoMapper;
import com.example.demo.dto.mapper.StatusDtoMapper;
import com.example.demo.dto.mapper.SubcontractorDtoMapper;
import com.example.demo.entity.GstStatusModelSubcontractor;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.EmailReminderMapper;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.mappers.SubcontractorMapper;
import com.example.demo.service.SubcontractorService;

@Service
public class SubcontractorServiceImpl implements SubcontractorService {
	private final SubcontractorDtoMapper subcontractorDtoMapper;
	private final SubcontractorMapper subcontractorMapper;
	private final StatusDtoMapper statusDtoMapper;
	private final StatusMapper statusMapper;
	private final ServiceProviderMapper serviceProviderMapper;
	private final EmailReminderMapper emailReminderMapper;
	private final GstStatusModelSubcontractorDtoMapper gstStatusModelSubcontractorDtoMapper;
	private static final Logger log = LoggerFactory.getLogger(SubcontractorServiceImpl.class);


	public SubcontractorServiceImpl(SubcontractorDtoMapper subcontractorDtoMapper,
			SubcontractorMapper subcontractorMapper, StatusDtoMapper statusDtoMapper, StatusMapper statusMapper,
			ServiceProviderMapper serviceProviderMapper,
			GstStatusModelSubcontractorDtoMapper gstStatusModelSubcontractorDtoMapper, EmailReminderMapper emailReminderMapper) {
		this.subcontractorDtoMapper = subcontractorDtoMapper;
		this.subcontractorMapper = subcontractorMapper;
		this.statusDtoMapper = statusDtoMapper;
		this.statusMapper = statusMapper;
		this.serviceProviderMapper = serviceProviderMapper;
		this.emailReminderMapper = emailReminderMapper;
		this.gstStatusModelSubcontractorDtoMapper = gstStatusModelSubcontractorDtoMapper;
	}

	@Override
	public int saveSubcontractor(SubcontractorDto subcontractorDtoTosave) throws GeneralException {
		subcontractorDtoTosave.setSCreationDate(LocalDateTime.now());
		Subcontractor subcontractorToSave = subcontractorDtoMapper.dtoToSubcontractor(subcontractorDtoTosave);
	    int isSubcontractorInserted = subcontractorMapper.insertSubcontractor(subcontractorToSave);
	    if (isSubcontractorInserted == 0) {
	        return isSubcontractorInserted;
	    }

	    // remarque qu'on persiste le sous-traitant, on génère l'id automatiquement et
	    // comme ça on peut retourner le correct sans prendre en considération l'id
	    // saisi par l'utilisateur (subcontractDto)

	    // Si l'insertion du nouveau sous-traitant en bdd se passe bien, on alimente la table intermédiaire qui va service pour les relances d'emails
	    int mmId = 1; // message model
	    
	    GstStatusModelSubcontractorDTO gstStatusModelSubcontractorDTO = new GstStatusModelSubcontractorDTO();
	    
	    gstStatusModelSubcontractorDTO.setStatusMsFkSubcontractorId(subcontractorToSave.getSId());
	    gstStatusModelSubcontractorDTO.setStatusMsFkMessageModelId(mmId);
	    gstStatusModelSubcontractorDTO.setStatusMsFkStatusId(subcontractorToSave.getStatus().getStId());
	    
	    GstStatusModelSubcontractor gstStatusModelSubcontractor = gstStatusModelSubcontractorDtoMapper.toGstStatusModelSubcontractor(gstStatusModelSubcontractorDTO);
	    try {
	        int isGstStatusModelSubcontractorInserted = emailReminderMapper.insertGstStatusModelSubcontractor(gstStatusModelSubcontractor);
	        if (isGstStatusModelSubcontractorInserted == 0) {
	            throw new GeneralException("Erreur lors de l'insertion des données dans la table intermédiaire des sous-traitants");
	        }
	        log.info("Données dans la table intermédiaire des sous-traitants insérées avec succès");
	        return subcontractorToSave.getSId();
	    } catch (PersistenceException e) {
	        log.error("Erreur MyBatis lors de l'insertion des données dans la table intermédiaire des sous-traitants", e);
	        throw new GeneralException("Erreur MyBatis lors de l'insertion des données dans la table intermédiaire des sous-traitants : " + e);
	    } catch (Exception e) {
	        log.error("Erreur lors du traitement de saveSubcontractor", e);
	        throw new GeneralException("Erreur lors du traitement de saveSubcontractor : " + e);
	    }
	}
	

	@Override
	public List<SubcontractorDto> getAllNonArchivedSubcontractors(String nameColonne, String sortingMethod, int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		Optional<List<Subcontractor>> optionalSubcontractorsList = Optional.ofNullable(subcontractorMapper.findAllNonArchivedSubcontractors(nameColonne, sortingMethod, offset, pageSize));
				
		if (optionalSubcontractorsList.isEmpty()) {
			throw new EntityNotFoundException("Il n'y a pas de sous-traitans enregistré");
		}
		return optionalSubcontractorsList.get().stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	}
	
	@Override
	public List<SubcontractorDto> getAllSubcontractorWithStatus(String nameColonne, String sortingMethod, int pageSize, int page, int statusId) {
		int offset = (page - 1) * pageSize;
		
		Optional<List<Subcontractor>> optionalSubcontractorsList = Optional.ofNullable(subcontractorMapper.findAllSubcontractorsWithStatus(nameColonne, sortingMethod, offset, pageSize, statusId));
		
		if (optionalSubcontractorsList.isEmpty()) {
			throw new EntityNotFoundException("Il n'y a pas de sous-traitans enregistré");
		}
		
		return optionalSubcontractorsList.get().stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	}
	
	@Override
	public List<StatusDto> getAllStatus() {
		Optional<List<Status>> optionalStatusList = Optional.ofNullable(statusMapper.getAllStatus());
		if (optionalStatusList.isEmpty()) {
			throw new EntityNotFoundException("Il n'y a pas de statut enregistré");
		}
		return optionalStatusList.get().stream().map(statusDtoMapper::statusToDto).toList();
	}

	
	@Override
	public Integer getNumberOfAllSubcontractors() {
		Integer numberOfFoundSubcontractors = subcontractorMapper.countAllNonArchivedSubcontractors();
		if (numberOfFoundSubcontractors == 0) {
			throw new EntityNotFoundException("il n'y a pas de sous-traiatant trouvé");
		}
		return numberOfFoundSubcontractors;
	}
	
	@Override
	public Integer getNumberOfAllSubcontractorsWithStatus(Integer statusId) {
		Integer numberOfFoundSubcontractorsWithStatus = subcontractorMapper.countAllNonArchivedSubcontractorsWithStatus(statusId);
		if (numberOfFoundSubcontractorsWithStatus == 0) {
			throw new EntityNotFoundException("il n'y a pas de sous-traiatant trouvé");
		}
		return numberOfFoundSubcontractorsWithStatus;

	}
	
	@Override
	public SubcontractorDto getSubcontractorWithStatus(int sId) {
		Optional<Subcontractor> optionalSubcontractor = Optional.ofNullable(subcontractorMapper.findSubcontractorWithStatusById(sId));
		if (optionalSubcontractor.isEmpty()) {
			throw new EntityNotFoundException("le sous-traitant avec l'id: " + sId + " n'existe pas!!");
		}
		return subcontractorDtoMapper.subcontractorToDto(optionalSubcontractor.get());
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
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(sName.toUpperCase());
		if (subcontractor == null) {
			return 0;
		}
		return subcontractor.getSId();
	}
	
	@Override
	public int checkIfSubcontractorExistBySEmail(String sEmail) {
		Subcontractor subcontractor = subcontractorMapper.findSubcontractorWithStatusBySubcontractorEmail(sEmail);
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
		Subcontractor foundSubcontractorBySName = subcontractorMapper.findSubcontractorWithStatusBySubcontractorName(sName);
		if (foundSubcontractorBySName == null) {
			throw new EntityNotFoundException("le sous-traitant avec le nom: " + sName + " n'existe pas!!");
		}
		return foundSubcontractorBySName;
	}

	@Override
	public List<SubcontractorDto> getAllSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms,
			int pageNumber, int pageSize, int statusId, String columnName, String sortingMethod) throws GeneralException {
	    // Calcul de l'offset pour la pagination
	    int offset = (pageNumber - 1) * pageSize;

	    // Vérification de l'attribut de recherche et récupération des prestataires en conséquence
	    if (columnName.equals("s_name")) {
	        if (statusId == 0) {
	            return subcontractorMapper.findAllSubcontractorsByCriteria("s.s_name", searchTerms, offset, pageSize, sortingMethod).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        } else {
	            return subcontractorMapper.findAllSubcontractorsByCriteriaAndFiltredByStatus("s.s_name", searchTerms, offset, pageSize, sortingMethod, statusId).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        }
	    } else if (columnName.equals("s_email")) {
	        if (statusId == 0) {
	            return subcontractorMapper.findAllSubcontractorsByCriteria("s.s_email", searchTerms, offset, pageSize, sortingMethod).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        } else {
	            return subcontractorMapper.findAllSubcontractorsByCriteriaAndFiltredByStatus("s.s_email",searchTerms, offset, pageSize, sortingMethod, statusId).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", columnName));
	    }
	}

	@Override
	public Integer getNumberOfSubcontractorsBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId, String columnName) throws GeneralException {
	    if (columnName.equals("s_name") || columnName.equals("s_email")) {
	        // Vérification de l'attribut de recherche et récupération du nombre de prestataires de services en conséquence
	        if (statusId == 0) {
	            return subcontractorMapper.countAllSubcontractorsByCriteria(columnName,searchTerms);
	        } else {
	            return subcontractorMapper.countAllSubcontractorsByCriteriaAndFiltredByStatus(columnName,searchTerms,statusId);
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", columnName));
	    }
	}

	@Override
	public int getPageNumberOfNewlyAddedOrUpdatedSubcontractor(int savedSubcontractorId, int pageSize) {
        int newPage = 1;
        int newIndex = -1;
		int countAllNonArchivedServiceProviders = subcontractorMapper.countAllNonArchivedSubcontractors();
        List<SubcontractorDto> sortedSubcontractors = subcontractorMapper.findAllNonArchivedSubcontractors("s_fk_status_id","asc", 0, countAllNonArchivedServiceProviders).stream().map(subcontractorDtoMapper::subcontractorToDto).toList();
        for (int i = 0; i < sortedSubcontractors.size(); i++) {
            if (sortedSubcontractors.get(i).getSId() == savedSubcontractorId) {
                newIndex = i;
                break;
            }
        }
        if (newIndex != -1) {
            // calculer le nombre de page en se basant sur l'indice et le nombre d'élément par page.
            newPage = (int) Math.ceil((double) (newIndex + 1) / pageSize);
        }
        return newPage;
	}
}

package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.StatusDto;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.dto.mapper.StatusDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.mappers.StatusMapper;
import com.example.demo.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
	private final ServiceProviderMapper serviceProviderMapper;
	private final ServiceProviderDtoMapper serviceProviderDtoMapper;
	private final StatusDtoMapper statusDtoMapper;
	private final StatusMapper statusMapper;

	public ServiceProviderServiceImpl(ServiceProviderMapper serviceProviderMapper,
			ServiceProviderDtoMapper serviceProviderDtoMapper, StatusDtoMapper statusDtoMapper,
			StatusMapper statusMapper) {
		this.serviceProviderMapper = serviceProviderMapper;
		this.serviceProviderDtoMapper = serviceProviderDtoMapper;
		this.statusDtoMapper = statusDtoMapper;
		this.statusMapper = statusMapper;
	}

	@Override
	public ServiceProviderDto getServiceProviderById(int serviceProviderId) {
		Optional<ServiceProvider> optionalServiceProviderById = Optional.ofNullable(serviceProviderMapper.findServiceProviderWithSubcontractorBySpId(serviceProviderId));
		if (optionalServiceProviderById.isEmpty()) {
			throw new EntityNotFoundException("le prestataire avec l'id: " + serviceProviderId + " n'existe pas!!");
		}
		return serviceProviderDtoMapper.serviceProviderToDto(optionalServiceProviderById.get());
	}
	
	@Override
	public List<ServiceProviderDto> getAllServiceProvidersBySubcontractorId(int subcontractorId) {
	    List<ServiceProvider> serviceProviders = Optional.ofNullable(serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorId))
	            .orElseThrow(() -> new EntityNotFoundException(String.format("Le sous-traitant avec l'id: %d n'a pas de prestataires", subcontractorId)));

	    if (serviceProviders.isEmpty()) {
	        throw new EntityNotFoundException(String.format("Le sous-traitant avec l'id: %d n'a pas de prestataires", subcontractorId));
	    }

	    return serviceProviders.stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	}
	
	@Override
	public List<ServiceProviderDto> getAllServiceProvidersWithOrWithoutStatus(String sortingMethod, int pageNumber,
	        int pageSize, int statusId) {
	    int offset = (pageNumber - 1) * pageSize;

	    List<ServiceProvider> serviceProvidersList;

	    if (statusId == 0) {
	        serviceProvidersList = serviceProviderMapper.findAllNonArchivedServiceProviders(sortingMethod, offset, pageSize);
	    } else if (statusId >= 1 && statusId <= 4) {
	        serviceProvidersList = serviceProviderMapper.findAllServiceProvidersFlitredByStatus(sortingMethod, offset, pageSize, statusId);
	    } else {
	        throw new EntityNotFoundException(String.format("Le statut avec l'id: %d n'existe pas", statusId));
	    }

	    if (serviceProvidersList == null || serviceProvidersList.isEmpty()) {
	        throw new EntityNotFoundException("Aucun résultat trouvé");
	    }

	    return serviceProvidersList.stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	}
	
	@Override
	public int countAllServiceProvidersWithOrWithoutStatus(int statusId) {
	    // Vérification du statut et comptage des prestataires en conséquence
		if (statusId == 0 ) {
			return serviceProviderMapper.countAllNonArchivedServiceProviders();
		} else if (statusId>=1 && statusId<=4) {
			return serviceProviderMapper.countAllServiceProvidersFiltredByStatus(statusId);			
		} else {
			throw new EntityNotFoundException(String.format("le statut avec l'id: %d n'existe pas", statusId));
		}
	}
	
	@Override
	public List<ServiceProviderDto> getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int pageNumber,
	        int pageSize, int statusId, String columnName) throws GeneralException {
	    int offset = (pageNumber - 1) * pageSize;

	    String criteriaColumn = getCriteriaColumn(columnName);

	    if (statusId == 0) {
	        return serviceProviderMapper.findServiceProvidersByCriteria(criteriaColumn, searchTerms, offset, pageSize)
	                .stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	    } else {
	        return serviceProviderMapper.findServiceProvidersByCriteriaAndFiltredByStatus(criteriaColumn, searchTerms, offset, pageSize, statusId)
	                .stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	    }
	}
	
	@Override
	public int countServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
	        String columnName) throws GeneralException {
	    String criteriaColumn = getCriteriaColumn(columnName);

	    if (statusId == 0) {
	        return serviceProviderMapper.countServiceProvidersByCriteria(criteriaColumn, searchTerms);
	    } else {
	        return serviceProviderMapper.countServiceProvidersByByCriteriaAndFiltredByStatus(criteriaColumn, searchTerms, statusId);
	    }
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
	public int saveServiceProvider(ServiceProviderDto serviceProviderDtoToSave) {
		ServiceProvider serviceProviderToSave = serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDtoToSave);
		serviceProviderToSave.setSpCreationDate(LocalDateTime.now());
		int isServiceProviderInserted = serviceProviderMapper.insertServiceProvider(serviceProviderToSave);
		if (isServiceProviderInserted == 0) {
			return 0;
		}
		return serviceProviderMapper.findServiceProviderBySpEmail(serviceProviderToSave.getSpEmail()).getSpId();
	}
	
	@Override
	public int updateServiceProvider(ServiceProviderDto serviceProviderDtoToUpdate) {
	    // Mise à jour de la date de dernière modification
		serviceProviderDtoToUpdate.setSpLastUpdateDate(LocalDateTime.now());
		
	    // Exécution de la mise à jour dans la base de données
		return serviceProviderMapper.updateServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDtoToUpdate));
	}
	
	@Override
	public int archiveServiceProvider(int serviceProviderId) throws AlreadyArchivedEntity {
		ServiceProviderDto serviceProviderDtoTArchive = getServiceProviderById(serviceProviderId);
		// Vérification si le prestataire est déjà archivé
		if (serviceProviderDtoTArchive.getSpStatus().getStId() == 4) {
			throw new AlreadyArchivedEntity(String.format("Erreur: le prestataire avec l'id %d est déjà archivé.", serviceProviderId));
		}
		
		return serviceProviderMapper.archiveServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDtoTArchive));
	}

	@Override
	public boolean checkIfServiceProviderExistById(int serviceProviderId) {
	    // Recherche du prestataire par son ID
		ServiceProvider foundServiceProviderById = serviceProviderMapper.findServiceProviderById(serviceProviderId);
		Boolean isServiceProviderExists = false;
		if (foundServiceProviderById != null) {
			isServiceProviderExists = true;
		}
		return isServiceProviderExists;
	}

	@Override
	public int checkIfSubcontractorExistBySpEmail(String serviceProviderSpEmail) {
		ServiceProvider foundServiceProvider = serviceProviderMapper.findServiceProviderBySpEmail(serviceProviderSpEmail);
		if (foundServiceProvider == null)
			return 0;
		return foundServiceProvider.getSpId();
	}

	@Override
	public void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto) {
	    // Vérification de l'existence d'un autre prestataire avec le même email
		int isServiceProviderExistBySpEmail = checkIfSubcontractorExistBySpEmail(serviceProviderDto.getSpEmail());
		
	    // Si un autre prestataire avec le même email existe et n'est pas le prestataire actuel en cours de mise à jour
		if (isServiceProviderExistBySpEmail != 0 && serviceProviderDto.getSpId() != isServiceProviderExistBySpEmail) {
			throw new EntityDuplicateDataException("l'émail du préstataire saisi existe déjà");
		}
	}

	@Override
	public void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto) {
	    // Vérification de l'existence d'un autre prestataire avec le même email
		int isServiceProviderExistBySpEmail = checkIfSubcontractorExistBySpEmail(serviceProviderDto.getSpEmail());
		
	    // Si un autre prestataire avec le même email existe
		if (isServiceProviderExistBySpEmail != 0) {
			throw new EntityDuplicateDataException("l'émail du préstataire saisi existe déjà");
		}
	}

	@Override
	public int getPageNumberOfNewlyAddedOrUpdatedServiceProvider(int savedServiceProviderId, int pageSize) {
        int newPage = 1;
        int newIndex = -1;
		int countAllNonArchivedServiceProviders = serviceProviderMapper.countAllNonArchivedServiceProviders();
        List<ServiceProviderDto> sortedServiceProviders = serviceProviderMapper.findAllNonArchivedServiceProviders("asc", 0, countAllNonArchivedServiceProviders).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
        for (int i = 0; i < sortedServiceProviders.size(); i++) {
            if (sortedServiceProviders.get(i).getSpId() == savedServiceProviderId) {
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
	
	@Override
	public void formattingServiceProviderData(ServiceProviderDto serviceProviderDto)
			throws GeneralException {
        serviceProviderDto.setSpFirstName(firstNameAndEmailFormatter(serviceProviderDto.getSpFirstName()));
        serviceProviderDto.setSpName(nameFormatter(serviceProviderDto.getSpName()));
        serviceProviderDto.setSpEmail(firstNameAndEmailFormatter(serviceProviderDto.getSpEmail()));
	}
	
	
	/**
	 * Formate le prénom ou l'email en mettant la première lettre en majuscule et le reste en minuscules.
	 *
	 * @param name La chaîne de caractères représentant le prénom ou l'email à formater.
	 * @return La chaîne de caractères formatée avec la première lettre en majuscule et le reste en minuscules.
	 * @throws GeneralException Si le prénom ou l'email est nul ou vide.
	 */
	private String firstNameAndEmailFormatter(String name) throws GeneralException {
	    // Vérification de la validité du prénom ou de l'email
		if (name == null || name.trim().isEmpty()) {
			throw new GeneralException("le nom est necessaire");
		}
		
	    // Formatage de la chaîne en mettant la première lettre en majuscule et le reste en minuscules
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	
	/**
	 * Formate le nom en mettant toutes les lettres en majuscules.
	 *
	 * @param name La chaîne de caractères représentant le nom à formater.
	 * @return La chaîne de caractères formatée avec toutes les lettres en majuscules.
	 * @throws GeneralException Si le nom est nul ou vide.
	 */
	private String nameFormatter(String name) throws GeneralException {
	    // Vérification de la validité du nom
		if (name == null || name.trim().isEmpty()) {
			throw new GeneralException("le nom est necessaire");
		}
		
	    // Formatage de la chaîne en mettant toutes les lettres en majuscules
		return name.toUpperCase();
	}

	
	/**
	 * Récupère la colonne de la base de données correspondante en fonction du nom de colonne donné.
	 *
	 * @param columnName Le nom de la colonne pour laquelle récupérer la colonne de la base de données.
	 * @return La colonne de la base de données correspondant au nom de colonne fourni.
	 * @throws GeneralException Si le nom de colonne fourni ne correspond à aucune colonne connue.
	 */
	private String getCriteriaColumn(String columnName) throws GeneralException {
	    switch (columnName) {
	        case "name":
	            return "sp.sp_name";
	        case "firstName":
	            return "sp.sp_first_name";
	        case "email":
	            return "sp.sp_email";
	        case "subcontractorName":
	            return "s.s_name";
	        default:
	            throw new GeneralException(String.format("Le champ %s n'existe pas", columnName));
	    }
	}
	
}

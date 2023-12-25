package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GstStatusModelServiceProviderDTO;
import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.mapper.GstStatusModelServiceProviderDtoMapper;
import com.example.demo.entity.GstStatusModelServiceProvider;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
	private final ServiceProviderMapper serviceProviderMapper;
	private final ServiceProviderDtoMapper serviceProviderDtoMapper;
	private final GstStatusModelServiceProviderDtoMapper gstStatusModelServiceProviderDtoMapper;
	private static final Logger log = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

	public ServiceProviderServiceImpl(ServiceProviderMapper serviceProviderMapper,
			ServiceProviderDtoMapper serviceProviderDtoMapper,
			GstStatusModelServiceProviderDtoMapper gstStatusModelServiceProviderDtoMapper) {
		this.serviceProviderMapper = serviceProviderMapper;
		this.serviceProviderDtoMapper = serviceProviderDtoMapper;
		this.gstStatusModelServiceProviderDtoMapper = gstStatusModelServiceProviderDtoMapper;
	}

	@Override
	public int saveServiceProvider(ServiceProviderDto serviceProviderDtoToSave) throws GeneralException {
		ServiceProvider serviceProviderToSave = serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDtoToSave);
		serviceProviderToSave.setSpCreationDate(LocalDateTime.now());
		int isServiceProviderInserted = serviceProviderMapper.insertServiceProvider(serviceProviderToSave);
		if (isServiceProviderInserted == 0) {
			return 0;
		}
		// remarque: qu'on persiste le prestataire, on génere l'id automatiquement et
		// comme ça on peut retourner le correct sans prendre en considération l'id
		// saisi par l'utilisateur
		
		// Si l'insertion du nouveau sous-traitant en bdd se passe bien, on alimente la table intermédiaire qui va service pour les relances d'emails
		int mmId = 1; // message modèle
		
		GstStatusModelServiceProviderDTO gstStatusModelServiceProviderDTO = new GstStatusModelServiceProviderDTO();
		
		gstStatusModelServiceProviderDTO.setStatusMspFkServiceProviderId(serviceProviderToSave.getSpId());
		gstStatusModelServiceProviderDTO.setStatusMspFkMessageModelId(mmId);
		gstStatusModelServiceProviderDTO.setStatusMspFkStatusId(serviceProviderToSave.getSpStatus().getStId());
		
		GstStatusModelServiceProvider gstStatusModelServiceProvider = gstStatusModelServiceProviderDtoMapper.toGstStatusModelSubcontractor(gstStatusModelServiceProviderDTO);
		
		try {
			int isGstStatusModelServiceProviderInserted = serviceProviderMapper.insertGstStatusModelServiceProvider(gstStatusModelServiceProvider);
			
			if (isGstStatusModelServiceProviderInserted == 0) {
				throw new GeneralException("Erreur lors de l'insertion des données dans la table intermédiaire des prestataires");
			}
			
			log.info("Données dans la table intermédiaire des prestataires insérées avec succès");
			
			return serviceProviderToSave.getSpId();
		} catch(PersistenceException e) {
			log.error("Erreur MyBatis lors de l'insertion des données dans la table intermédiaire des prestatires", e);
	        throw new GeneralException("Erreur MyBatis lors de l'insertion des données dans la table intermédiaire des prestatires : " + e);
		} catch(Exception e) {
			log.error("Erreur lors du traitement de saveServiceprovider", e);
	        throw new GeneralException("Erreur lors du traitement de saveServiceprovider : " + e);
		}
	}

	@Override
	public ServiceProviderDto getServiceProviderById(int serviceProviderId) {
		ServiceProviderDto foundedServiceProviderById = serviceProviderDtoMapper.serviceProviderToDto(serviceProviderMapper
				.findServiceProviderWithSubcontractorBySpId(serviceProviderId));
		if (foundedServiceProviderById == null) {
			throw new EntityNotFoundException("le prestataire avec l'id: " + serviceProviderId + " n'existe pas!!");
		}
		return foundedServiceProviderById;
	}

	@Override
	public int updateServiceProvider(ServiceProviderDto serviceProviderDtoToUpdate) {
	    // Mise à jour de la date de dernière modification
		serviceProviderDtoToUpdate.setSpLastUpdateDate(LocalDateTime.now());
		
	    // Exécution de la mise à jour dans la base de données
		return serviceProviderMapper.updateServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDtoToUpdate));
	}

	@Override
	public int archiveServiceProvider(ServiceProviderDto serviceProviderDtoToArchive) {
		return serviceProviderMapper.archiveServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDtoToArchive));
	}
	
	@Override
	public List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId) {
	    // Récupération des prestataires associés au sous-traitant par son ID
	    List<ServiceProvider> serviceProviders = serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorId);

	    // Conversion des prestataires en DTO
	    return serviceProviders;	
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
	public String firstNameAndEmailFormatter(String name) throws GeneralException {
	    // Vérification de la validité du prénom ou de l'email
		if (name == null || name.trim().isEmpty()) {
			throw new GeneralException("le nom est necessaire");
		}
		
	    // Formatage de la chaîne en mettant la première lettre en majuscule et le reste en minuscules
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	@Override
	public String nameFormatter(String name) throws GeneralException {
	    // Vérification de la validité du nom
		if (name == null || name.trim().isEmpty()) {
			throw new GeneralException("le nom est necessaire");
		}
		
	    // Formatage de la chaîne en mettant toutes les lettres en majuscules
		return name.toUpperCase();
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
	public List<ServiceProviderDto> getAllServiceProvidersWithOrWithoutStatus(String sortingMethod, int pageNumber,
			int pageSize, int statusId) {
	    // Calcul de l'offset pour la pagination
		int offset = (pageNumber - 1) * pageSize;
		
	    // Vérification du statut et récupération des prestataires en conséquence
		if (statusId == 0 ) {
			return serviceProviderMapper.findAllNonArchivedServiceProviders(sortingMethod, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
		} else if (statusId>=1 && statusId<=4) {
			return serviceProviderMapper.findAllServiceProvidersFlitredByStatus(sortingMethod, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
		} else {
			throw new EntityNotFoundException(String.format("le statut avec l'id: %d n'existe pas", statusId));
		}
	}

	
	@Override
	public List<ServiceProviderDto> getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int pageNumber,
	        int pageSize, int statusId, String columnName) throws GeneralException {
	    // Calcul de l'offset pour la pagination
	    int offset = (pageNumber - 1) * pageSize;

	    // Vérification de l'attribut de recherche et récupération des prestataires en conséquence
	    if (columnName.equals("name")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findServiceProvidersByCriteria("sp.sp_name",searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByCriteriaAndFiltredByStatus("sp.sp_name",searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else if (columnName.equals("firstName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findServiceProvidersByCriteria("sp.sp_first_name",searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByCriteriaAndFiltredByStatus("sp.sp_first_name",searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else if (columnName.equals("email")) {
	    	if (statusId == 0) {
	    		return serviceProviderMapper.findServiceProvidersByCriteria("sp.sp_email",searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	    	} else {
	    		return serviceProviderMapper.findAllServiceProvidersByCriteriaAndFiltredByStatus("sp.sp_email",searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	    	}
	    } else if (columnName.equals("subcontractorName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findServiceProvidersByCriteria("s.s_name",searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByCriteriaAndFiltredByStatus("s.s_name",searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", columnName));
	    }
	}

	@Override
	public int getNumberOfServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String columnName) throws GeneralException {
	    if (columnName.equals("name")) {
	        // Vérification de l'attribut de recherche et récupération du nombre de prestataires de services en conséquence
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByCriteria("sp.sp_name",searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByByCriteriaAndFiltredByStatus("sp.sp_name",searchTerms,statusId);
	        }
	    } else if (columnName.equals("firstName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByCriteria("sp.sp_first_name",searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByByCriteriaAndFiltredByStatus("sp.sp_first_name",searchTerms,statusId);
	        }
	    } else if (columnName.equals("email")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByCriteria("sp.sp_email",searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByByCriteriaAndFiltredByStatus("sp.sp_email",searchTerms,statusId);
	        }
	    } else if (columnName.equals("subcontractorName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByCriteria("s.s_name",searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByByCriteriaAndFiltredByStatus("s.s_name",searchTerms,statusId);
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", columnName));
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
	
}

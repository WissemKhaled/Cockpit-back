package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	private ServiceProviderMapper serviceProviderMapper;
	private ServiceProviderDtoMapper serviceProviderDtoMapper;

	public ServiceProviderServiceImpl(ServiceProviderMapper serviceProviderMapper) {
		this.serviceProviderMapper = serviceProviderMapper;
	}

	@Override
	public ServiceProvider getServiceProviderById(int serviceProviderId) {
		ServiceProvider foundedServiceProviderById = serviceProviderMapper
				.findServiceProviderWithSubcontractorBySpId(serviceProviderId);
		if (foundedServiceProviderById == null) {
			throw new EntityNotFoundException("le prestataire avec l'id: " + serviceProviderId + " n'existe pas!!");
		}
		return foundedServiceProviderById;
	}
	
	@Override
	public int saveServiceProvider(ServiceProvider serviceProviderToSave) {
	    // Définition de la date de création
		serviceProviderToSave.setSpCreationDate(LocalDateTime.now());
		
	    // Insertion du prestataire dans la base de données
		int isServiceProviderInserted = serviceProviderMapper.insertServiceProvider(serviceProviderToSave);
		
	    // Vérification si l'enregistrement a réussi
		if (isServiceProviderInserted == 0) {
			return 0;
		}
	    // Remarque: L'ID du prestataire est généré automatiquement lors de l'enregistrement,
	    // permettant de le retourner sans prendre en compte l'ID saisi par l'utilisateur.
		return serviceProviderToSave.getSpId();
	}

	@Override
	public int updateServiceProvider(ServiceProvider serviceProviderToUpdate) {
	    // Mise à jour de la date de dernière modification
		serviceProviderToUpdate.setSpLastUpdateDate(LocalDateTime.now());
		
	    // Exécution de la mise à jour dans la base de données
		return serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate);
	}

	@Override
	public int archiveServiceProvider(ServiceProvider serviceProviderIdToArchive) {
		return serviceProviderMapper.archiveServiceProvider(serviceProviderIdToArchive);
	}
	
	@Override
	public List<ServiceProviderDto> getServiceProvidersBySubcontractorId(int subcontractorId) {
	    // Récupération des prestataires associés au sous-traitant par son ID
	    List<ServiceProvider> serviceProviders = serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorId);

	    // Conversion des prestataires en DTO
	    return serviceProviders.stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();	
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
		ServiceProvider foundServiceProvider = serviceProviderMapper
				.findServiceProviderBySpEmail(serviceProviderSpEmail);
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
	        int pageSize, int statusId, String searchAttribute) throws GeneralException {
	    // Calcul de l'offset pour la pagination
	    int offset = (pageNumber - 1) * pageSize;

	    // Vérification de l'attribut de recherche et récupération des prestataires en conséquence
	    if (searchAttribute.equals("name")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findAllServiceProvidersByServiceProviderName(searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByNameAndFiltredStatus(searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else if (searchAttribute.equals("email")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findAllServiceProvidersByServiceProviderFirstName(searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByFirstNameAndFiltredStatus(searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else if (searchAttribute.equals("firstName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findAllServiceProvidersByServiceProviderEmail(searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByEmailAndStatus(searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else if (searchAttribute.equals("subcontractorName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findServiceProvidersBySubcontractorName(searchTerms, offset, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersBySubcontractorNameAndStatus(searchTerms, offset, pageSize, statusId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", searchAttribute));
	    }
	}

	@Override
	public int getNumberOfServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String searchAttribute) throws GeneralException {
	    if (searchAttribute.equals("name")) {
	        // Vérification de l'attribut de recherche et récupération du nombre de prestataires de services en conséquence
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByServiceProviderName(searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByNameAndFiltredByStatus(searchTerms,statusId);
	        }
	    } else if (searchAttribute.equals("email")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByServiceProviderFirstName(searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByEmailAndFiltredByStatus(searchTerms,statusId);
	        }
	    } else if (searchAttribute.equals("firstName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByServiceProviderEmail(searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersByFirstNameAndFiltredByStatus(searchTerms,statusId);
	        }
	    } else if (searchAttribute.equals("subcontractorName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersBySubcontractorName(searchTerms);
	        } else {
	            return serviceProviderMapper.findNumberOfAllServiceProvidersBySubcontractorNameAndFiltredByStatus(searchTerms,statusId);
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", searchAttribute));
	    }
	}
	
}

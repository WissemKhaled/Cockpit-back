package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.ServiceProviderMapper;
import com.example.demo.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	private ServiceProviderMapper serviceProviderMapper;

	public ServiceProviderServiceImpl(ServiceProviderMapper serviceProviderMapper) {
		this.serviceProviderMapper = serviceProviderMapper;
	}

	@Override
	public int saveServiceProvider(ServiceProvider serviceProviderToSave) {
		serviceProviderToSave.setSpCreationDate(LocalDateTime.now());
		int isServiceProviderInserted = serviceProviderMapper.insertServiceProvider(serviceProviderToSave);
		if (isServiceProviderInserted == 0) {
			return 0;
		}
		// remarque: qu'on persiste le prestataire, on génere l'id automatiquement et
		// comme ça on peut retourner le correct sans prendre en considération l'id
		// saisi par l'utilisateur
		return serviceProviderToSave.getSpId();
	}

	@Override
	public int updateServiceProvider(ServiceProvider serviceProviderToUpdate) {
		serviceProviderToUpdate.setSpLastUpdateDate(LocalDateTime.now());
		return serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate);
	}

	@Override
	public int archiveServiceProvider(ServiceProvider serviceProviderIdToArchive) {
		return serviceProviderMapper.archiveServiceProvider(serviceProviderIdToArchive);
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
	public List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId) {
		return serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorId);
	}


	@Override
	public boolean checkIfServiceProviderExistById(int serviceProviderId) {
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
		int isServiceProviderExistBySpEmail = checkIfSubcontractorExistBySpEmail(serviceProviderDto.getSpEmail());
		if (isServiceProviderExistBySpEmail != 0 && serviceProviderDto.getSpId() != isServiceProviderExistBySpEmail) {
			throw new EntityDuplicateDataException("l'émail du préstataire saisi existe déjà");
		}
	}

	@Override
	public void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto) {
		int isServiceProviderExistBySpEmail = checkIfSubcontractorExistBySpEmail(serviceProviderDto.getSpEmail());
		if (isServiceProviderExistBySpEmail != 0) {
			throw new EntityDuplicateDataException("l'émail du préstataire saisi existe déjà");
		}
	}

	@Override
	public String firstNameAndEmailFormatter(String name) throws GeneralException {
		if (name == null || name.trim().isEmpty()) {
			throw new GeneralException("le nom est necessaire");
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	@Override
	public String nameFormatter(String name) throws GeneralException {
		if (name == null || name.trim().isEmpty()) {
			throw new GeneralException("le nom est necessaire");
		}
		return name.toUpperCase();
	}


	@Override
	public int countAllServiceProvidersFiltredByStatus(int statusId) {
		if (statusId == 0 ) {
			return serviceProviderMapper.countAllNonArchivedServiceProviders();
		} else if (statusId>=1 && statusId<=4) {
			return serviceProviderMapper.countAllServiceProvidersFiltredByStatus(statusId);			
		} else {
			throw new EntityNotFoundException(String.format("le statut avec l'id: %d n'existe pas", statusId));
		}
	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersFiltredByStatus(String sortingMethod, int pageNumber,
			int pageSize, int statusId) {
		int offset = (pageNumber - 1) * pageSize;
		if (statusId == 0 ) {
			return serviceProviderMapper.findAllNonArchivedServiceProviders(sortingMethod, offset, pageSize);
		} else if (statusId>=1 && statusId<=4) {
			return serviceProviderMapper.findAllServiceProvidersFlitredByStatus(sortingMethod, offset, pageSize, statusId);
		} else {
			throw new EntityNotFoundException(String.format("le statut avec l'id: %d n'existe pas", statusId));
		}
	}

	
	@Override
	public List<ServiceProvider> getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int pageNumber,
	        int pageSize, int statusId, String searchAttribute) throws GeneralException {
	    int offset = (pageNumber - 1) * pageSize;

	    if (searchAttribute.equals("name")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findAllServiceProvidersByServiceProviderName(searchTerms, offset, pageSize);
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByNameAndFiltredStatus(searchTerms, offset, pageSize, statusId);
	        }
	    } else if (searchAttribute.equals("email")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findAllServiceProvidersByServiceProviderFirstName(searchTerms, offset, pageSize);
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByFirstNameAndFiltredStatus(searchTerms, offset, pageSize, statusId);
	        }
	    } else if (searchAttribute.equals("firstName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findAllServiceProvidersByServiceProviderEmail(searchTerms, offset, pageSize);
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersByEmailAndStatus(searchTerms, offset, pageSize, statusId);
	        }
	    } else if (searchAttribute.equals("subcontractorName")) {
	        if (statusId == 0) {
	            return serviceProviderMapper.findServiceProvidersBySubcontractorName(searchTerms, offset, pageSize);
	        } else {
	            return serviceProviderMapper.findAllServiceProvidersBySubcontractorNameAndStatus(searchTerms, offset, pageSize, statusId);
	        }
	    } else {
	        throw new GeneralException(String.format("le champs %s n'existe pas", searchAttribute));
	    }
	}

	@Override
	public int getNumberOfServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String searchAttribute) throws GeneralException {
	    if (searchAttribute.equals("name")) {
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

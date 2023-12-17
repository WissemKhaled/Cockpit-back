package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
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
	public List<ServiceProvider> getAllServiceProviders() {
		return serviceProviderMapper.findAllServiceProviders();
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
	public String firstNameAndEmailFormatter(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new RuntimeException("le nom est necessaire");
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	@Override
	public String nameFormatter(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new RuntimeException("le nom est necessaire");
		}
		return name.toUpperCase();
	}

	@Override
	public int archiveServiceProvider(ServiceProvider serviceProviderIdToArchive) {
		return serviceProviderMapper.archiveServiceProvider(serviceProviderIdToArchive);
	}

	@Override
	public List<ServiceProvider> getAllNonArchivedServiceProviders(String sortingMethod, int pageNumber, int pageSize) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllNonArchivedServiceProviders(sortingMethod, offset, pageSize);
	}

	@Override
	public int countAllNonArchivedServiceProviders() {
		return serviceProviderMapper.countAllNonArchivedServiceProviders();
	}

	@Override
	public int countAllServiceProvidersFiltredByStatus(int statusId) {
		return serviceProviderMapper.countAllServiceProvidersFiltredByStatus(statusId);
	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersFiltredByStatus(String sortingMethod, int pageNumber,
			int pageSize, int statusId) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersFlitredByStatus(sortingMethod, offset, pageSize, statusId);
	}

	@Override
	public List<ServiceProvider> getServiceProvidersBySubcontractorSName(String sName, String sorting, int pageNumber,
			int pageSize) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findServiceProvidersBySubcontractorSName(sName.toUpperCase(), sorting, offset,
				pageSize);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersBySubcontractorSName(String sName) {
		return serviceProviderMapper.findNumberOfAllServiceProvidersBySubcontractorSName(sName.toUpperCase());
	}

	@Override
	public Integer getNumberOfAllServiceProvidersBySubcontractorSNameAndFiltredByStatus(String sName, int statusId) {
		return serviceProviderMapper
				.findNumberOfAllServiceProvidersBySubcontractorSNameAndFiltredByStatus(sName.toUpperCase(), statusId);
	}

	@Override
	public List<ServiceProvider> getServiceProvidersBySubcontractorSNameAndStatus(String sName, int pageNumber,
			int pageSize, int statusId) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersBySubcontractorSNameAndStatus(sName.toUpperCase(), offset,
				pageSize, statusId);
	}

	@Override
	public List<ServiceProvider> getServiceProvidersByServiceProviderEmail(String spEmail,
			int pageNumber, int pageSize) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersByServiceProviderEmail(spEmail, offset,
				pageSize);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersByServiceProviderEmail(String spEmail) {
		return serviceProviderMapper.findNumberOfAllServiceProvidersByServiceProviderEmail(spEmail);
	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersByServiceProviderName(String spName, int pageNumber, int pageSize) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersByServiceProviderName(spName, offset,
				pageSize);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersByServiceProviderName(String spName) {
		return serviceProviderMapper.findNumberOfAllServiceProvidersByServiceProviderName(spName);

	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersByServiceProviderFirstName(String spFirstName, int pageNumber,
			int pageSize) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersByServiceProviderFirstName(spFirstName, offset,
				pageSize);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersByServiceProviderFirstName(String spFirstName) {
		return serviceProviderMapper.findNumberOfAllServiceProvidersByServiceProviderFirstName(spFirstName);
	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersByNameAndStatus(String spName, int pageNumber, int pageSize,
			int statusId) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersByNameAndFiltredStatus(spName.toUpperCase(), offset,
				pageSize, statusId);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersByNameAndFiltredByStatus(String spName, int statusId) {
		return serviceProviderMapper
				.findNumberOfAllServiceProvidersByNameAndFiltredByStatus(spName.toUpperCase(), statusId);
	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersByEmailAndStatus(String spEmail, int pageNumber, int pageSize,
			int statusId) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersByEmailAndStatus(spEmail, offset,
				pageSize, statusId);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersByEmailAndFiltredByStatus(String spEmail, int statusId) {
		return serviceProviderMapper
				.findNumberOfAllServiceProvidersByEmailAndFiltredByStatus(spEmail, statusId);
	}

	@Override
	public List<ServiceProvider> getAllServiceProvidersByFirstNameAndStatus(String spFirstName, int pageNumber,
			int pageSize, int statusId) {
		int offset = (pageNumber - 1) * pageSize;
		return serviceProviderMapper.findAllServiceProvidersByFirstNameAndFiltredStatus(spFirstName, offset,
				pageSize, statusId);
	}

	@Override
	public Integer getNumberOfAllServiceProvidersByFirstNameAndFiltredByStatus(String spFirstName, int statusId) {
		return serviceProviderMapper
				.findNumberOfAllServiceProvidersByFirstNameAndFiltredByStatus(spFirstName, statusId);
	}

}

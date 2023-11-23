package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.ServiceProviderNotFoundException;
import com.example.demo.exception.SubcontractorNotFoundException;
import com.example.demo.mappers.ServiceProviderMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log
public class ServiceProviderServiceImpl implements ServiceProviderService {
	
	private ServiceProviderMapper serviceProviderMapper;

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
	public int archiveServiceProvider(ServiceProvider serviceProviderToArchive) {
		return serviceProviderMapper.archiveServiceProvider(serviceProviderToArchive);
	}

	@Override
	public int updateServiceProvider(ServiceProvider serviceProviderToUpdate) {
		return serviceProviderMapper.updateServiceProvider(serviceProviderToUpdate);
	}
	
	@Override
	public ServiceProvider getServiceProviderById(int serviceProviderId) {
		ServiceProvider foundedSubcontractorById = serviceProviderMapper.findServiceProviderById(serviceProviderId);
		if (foundedSubcontractorById == null) {
			throw new ServiceProviderNotFoundException("le prestataire avec l'id: " + serviceProviderId + " n'existe pas!!");
		}
		return foundedSubcontractorById;
	}

	@Override
	public List<ServiceProvider>  getServiceProvidersBySubcontractorId(int subcontractorId) {
		List<ServiceProvider> foundServiceProviderBySubcontractorId = serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorId);
		if (foundServiceProviderBySubcontractorId.isEmpty()) {
			throw new ServiceProviderNotFoundException("le sous-traitant avec l'id: " + subcontractorId + " n'a pas de prestataires");
		}
		return foundServiceProviderBySubcontractorId;
	}

	@Override
	public boolean checkIfServiceProviderExist(int serviceProviderId) {
		ServiceProvider foundServiceProviderById = serviceProviderMapper.findServiceProviderById(serviceProviderId);
		Boolean isServiceProviderExists = false;
		if (foundServiceProviderById != null) {
			isServiceProviderExists = true;
		}
		return isServiceProviderExists;
	}

}

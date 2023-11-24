package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.entity.Subcontractor;
import com.example.demo.exception.ServiceProviderNotFoundException;
import com.example.demo.exception.SubcontractorDuplicateDataException;
import com.example.demo.exception.SubcontractorNotFoundException;
import com.example.demo.mappers.ServiceProviderMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
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
	public int archiveServiceProvider(ServiceProvider serviceProviderIdToArchive) {
		return serviceProviderMapper.archiveServiceProvider(serviceProviderIdToArchive);
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
		return  serviceProviderMapper.findServiceProvidersBySubcontractorId(subcontractorId);
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
		ServiceProvider foundServiceProvider = serviceProviderMapper.findServiceProviderBySpEmail(serviceProviderSpEmail);
		if (foundServiceProvider == null) return 0;
		return foundServiceProvider.getSpId();
	}

	@Override
	public void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto) {
		int isServiceProviderExistBySpEmail = checkIfSubcontractorExistBySpEmail(serviceProviderDto.getSpEmail());
		if ( isServiceProviderExistBySpEmail != 0 && serviceProviderDto.getSpId() != isServiceProviderExistBySpEmail) {
			throw new SubcontractorDuplicateDataException("l'émail du préstataire saisi existe déjà");
		}
	}

	@Override
	public void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto) {
		int isServiceProviderExistBySpEmail = checkIfSubcontractorExistBySpEmail(serviceProviderDto.getSpEmail());
		if ( isServiceProviderExistBySpEmail != 0) {
			throw new SubcontractorDuplicateDataException("l'émail du préstataire saisi existe déjà");
		}		
	}

}

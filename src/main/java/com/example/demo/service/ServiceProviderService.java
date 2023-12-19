package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.GeneralException;

public interface ServiceProviderService {

	int saveServiceProvider(ServiceProvider serviceProviderToSave);

	int updateServiceProvider(ServiceProvider serviceProviderToUpdate);

	int archiveServiceProvider(ServiceProvider serviceProviderToArchive);
	
	ServiceProvider getServiceProviderById(int serviceProviderId);

	List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId);

	boolean checkIfServiceProviderExistById(int serviceProviderId);

	void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto);

	void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto);

	int checkIfSubcontractorExistBySpEmail(String serviceProviderSpEmail);

	String firstNameAndEmailFormatter(String name) throws GeneralException;

	String nameFormatter(String name) throws GeneralException;

	List<ServiceProvider> getAllServiceProvidersFiltredByStatus(String sortingMethod, int pageNumber, int pageSize,
			int statusId);

	List<ServiceProvider> getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int pageNumber,
			int pageSize, int statusId, String searchAttribute) throws GeneralException;
		
	int countAllServiceProvidersFiltredByStatus(int statusId);

	int getNumberOfServiceProvidersBySearchAndWithOrWithoutStatusFiltring(String searchTerms, int statusId,
			String searchAttribute) throws GeneralException;

}

package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;

public interface ServiceProviderService {

	int saveServiceProvider(ServiceProvider serviceProviderToSave);

	int updateServiceProvider(ServiceProvider serviceProviderToUpdate);

	ServiceProvider getServiceProviderById(int serviceProviderId);

	List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId);
	
	List<ServiceProvider> getServiceProvidersBySubcontractorSName(String sName);

	boolean checkIfServiceProviderExistById(int serviceProviderId);

	void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto);

	void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto);

	int checkIfSubcontractorExistBySpEmail(String serviceProviderSpEmail);

	String firstNameAndEmailFormatter(String name);

	String nameFormatter(String name);

	List<ServiceProvider> getAllServiceProviders();
	
	int archiveServiceProvider(ServiceProvider serviceProviderToArchive);

	List<ServiceProvider> getAllNonArchivedServiceProviders(String selectedStatusId, String sorting, int page,
			int pageSize);

	int countAllNonArchivedServiceProviders();

	int countAllServiceProvidersFiltredByStatus(int statusId);

	List<ServiceProvider> getAllServiceProvidersFiltredByStatus(String selectedStatusId, String sortingMethod,
			int pageNumber, int pageSize, int statusId);
}

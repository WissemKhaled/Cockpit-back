package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.GeneralException;

public interface ServiceProviderService {

	int saveServiceProvider(ServiceProvider serviceProviderToSave) throws GeneralException;

	int updateServiceProvider(ServiceProvider serviceProviderToUpdate);

	ServiceProvider getServiceProviderById(int serviceProviderId);

	List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId);

	List<ServiceProvider> getServiceProvidersBySubcontractorSName(String sName, String sorting, int pageNumber,
			int pageSize);

	Integer getNumberOfAllServiceProvidersBySubcontractorSName(String sName);

	boolean checkIfServiceProviderExistById(int serviceProviderId);

	void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto);

	void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto);

	int checkIfSubcontractorExistBySpEmail(String serviceProviderSpEmail);

	String firstNameAndEmailFormatter(String name);

	String nameFormatter(String name);

	List<ServiceProvider> getAllServiceProviders();

	int archiveServiceProvider(ServiceProvider serviceProviderToArchive);

	List<ServiceProvider> getAllNonArchivedServiceProviders(String sorting, int page, int pageSize);

	int countAllNonArchivedServiceProviders();

	int countAllServiceProvidersFiltredByStatus(int statusId);

	List<ServiceProvider> getAllServiceProvidersFiltredByStatus(String sortingMethod, int pageNumber, int pageSize,
			int statusId);

	Integer getNumberOfAllServiceProvidersBySubcontractorSNameAndFiltredByStatus(String sName, int statusId);

	List<ServiceProvider> getServiceProvidersBySubcontractorSNameAndStatus(String sName, int pageNumber, int pageSize,
			int statusId);

}

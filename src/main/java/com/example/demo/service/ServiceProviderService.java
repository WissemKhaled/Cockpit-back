package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ServiceProvider;

public interface ServiceProviderService {

	int saveServiceProvider(ServiceProvider serviceProviderToSave);

	int archiveServiceProvider(ServiceProvider serviceProviderToArchive);

	int updateServiceProvider(ServiceProvider serviceProviderToUpdate);

	ServiceProvider getServiceProviderById(int serviceProviderId);

	List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId);

	boolean checkIfServiceProviderExist(int serviceProviderId);

}

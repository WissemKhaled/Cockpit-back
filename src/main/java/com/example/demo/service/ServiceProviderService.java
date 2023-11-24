package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.entity.ServiceProvider;

public interface ServiceProviderService {

	int saveServiceProvider(ServiceProvider serviceProviderToSave);

	int archiveServiceProvider(ServiceProvider serviceProviderToArchive);

	int updateServiceProvider(ServiceProvider serviceProviderToUpdate);

	ServiceProvider getServiceProviderById(int serviceProviderId);

	List<ServiceProvider> getServiceProvidersBySubcontractorId(int subcontractorId);

	boolean checkIfServiceProviderExistById(int serviceProviderId);

	void handleServiceProviderUpdating(ServiceProviderDto serviceProviderDto);

	void handleServiceProviderSaving(ServiceProviderDto serviceProviderDto);

	int checkIfSubcontractorExistBySpEmail(String serviceProviderSpEmail);

}

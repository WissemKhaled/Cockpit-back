package com.example.demo.service;

import com.example.demo.entity.ServiceProvider;

public interface ServiceProviderService {

	int saveServiceProvider(ServiceProvider serviceProviderToSave);

	int archiveServiceProvider(ServiceProvider serviceProviderToArchive);

	int updateServiceProvider(ServiceProvider serviceProviderToUpdate);

}

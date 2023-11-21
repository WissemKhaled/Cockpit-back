package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ServiceProvider;
import com.example.demo.entity.Status;
import com.example.demo.mappers.ServiceProviderMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {
	
	private ServiceProviderMapper serviceProviderMapper;

	@Override
	public int saveServiceProvider(ServiceProvider serviceProviderToSave) {
		serviceProviderToSave.setSpCreationDate(LocalDateTime.now());
		int isServiceProviderInserted = serviceProviderMapper.insert(serviceProviderToSave);
		if (isServiceProviderInserted == 0) {
			return 0;			
		}
		return serviceProviderToSave.getSpId();
	}

	@Override
	public int archiveServiceProvider(ServiceProvider serviceProviderToArchive) {
		return serviceProviderMapper.archive(serviceProviderToArchive);
	}

}

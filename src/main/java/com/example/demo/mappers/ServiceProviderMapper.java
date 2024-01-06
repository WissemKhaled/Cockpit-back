package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.ServiceProvider;

@Mapper
public interface ServiceProviderMapper {
	
	ServiceProvider findServiceProviderWithSubcontractorBySpId(int sId);
	
	ServiceProvider findServiceProviderById(int spId);
	
	ServiceProvider findServiceProviderBySpEmail(String spEmail);

	List<ServiceProvider> findServiceProvidersBySubcontractorId(int sId);
	
	List<ServiceProvider> findServiceProvidersByCriteria(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize") int offset,
			@Param("offset") int pageSize);
	
	List<ServiceProvider> findServiceProvidersByCriteriaAndFiltredByStatus(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize, 
			@Param("statusId") int statusId);
	
	int countServiceProvidersByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms);

	int countServiceProvidersByByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("statusId") int statusId);
	
	List<ServiceProvider> findAllNonArchivedServiceProviders(
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize);
	
	List<ServiceProvider> findAllServiceProvidersFlitredByStatus(
			@Param("sorting") String sorting,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize, 
			@Param("statusId") int statusId);
	
	
	int insertServiceProvider(ServiceProvider serviceProvider);
	
	int updateServiceProvider(ServiceProvider serviceProvider);
	
	int archiveServiceProvider(ServiceProvider serviceProvider);
	
	int countAllNonArchivedServiceProviders();
	
	int countAllServiceProvidersFiltredByStatus(int statusId);

}

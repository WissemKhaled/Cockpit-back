package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Subcontractor;

@Mapper
public interface SubcontractorMapper {
	// ce code permet de renvoyer le nombre total de colonne de la
	// table subcontractor
	Integer countAllNonArchivedSubcontractors();
		
	
	Integer countAllNonArchivedSubcontractorsWithStatus(@Param("idStatus") Integer idStatus);

	// ce code permet de renvoyer une liste de sous-traitans avec la
	// pagination est le tri grave à la requette SQL
	List<Subcontractor> findAllNonArchivedSubcontractors(
			@Param("nameColonne") String nameColonne, 
			@Param("sortingMethod") String sortingMethod,
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize);
	
	
	// ce code permet de renvoyer une liste de sous-traitans avec la
	// pagination est le tri grave à la requette SQL
	List<Subcontractor> findAllSubcontractorsWithStatus(
			@Param("nameColonne") String nameColonne,
			@Param("sortingMethod") String sortingMethod, 
			@Param("pageSize") int offset, 
			@Param("offset") int pageSize,
			@Param("statusId") int statusId);

	
	Subcontractor findSubcontractorWithStatusById(int sId);

	
	Subcontractor findSubcontractorWithStatusBySubcontractorName(@Param("sName") String sName);

	
	Subcontractor findSubcontractorWithStatusBySubcontractorEmail(@Param("sEmail") String sEmail);


	int insertSubcontractor(Subcontractor subcontractor);

	
	int updateSubcontractor(Subcontractor subcontractor);

	
	int archiveSubcontractor(Subcontractor subcontractorToArchive);


	List<Subcontractor> findAllSubcontractorsByCriteria(
	        @Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("pageSize")int offset,
			@Param("offset") int pageSize,
			@Param("sortingMethod") String sortingMethod);
	

	List<Subcontractor> findAllSubcontractorsByCriteriaAndFiltredByStatus(
	        @Param("columnName") String columnName,
	        @Param("searchTerms") String searchTerms,
	        @Param("pageSize") int offset,
	        @Param("offset") int pageSize,
	        @Param("sortingMethod") String sortingMethod,
	        @Param("statusId") int statusId);


	Integer countAllSubcontractorsByCriteria(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms);
	

	Integer countAllSubcontractorsByCriteriaAndFiltredByStatus(
			@Param("columnName") String columnName,
			@Param("searchTerms") String searchTerms, 
			@Param("statusId") int statusId);

}

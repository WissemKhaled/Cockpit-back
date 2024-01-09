package com.example.demo.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Status;

@Mapper
public interface StatusMapper {
	
	/**
	 * renvoie le statut par son ID
	 * 
	 * @param stId l'ID du statut à chercher
	 * @return le statut trouvé
	 */
	Status findStatusById(@Param("stId") int stId);

	/**
	 * renvoie tous les statuts enregistrés
	 * 
	 * @return
	 */
	List<Status> getAllStatus();
}

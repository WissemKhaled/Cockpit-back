package com.example.demo.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.UUser;

@Mapper
public interface UUserMapper {

	/**
	 * renvoie un optional user à partir de son émail.
	 * 
	 * @param uEmail l'émail de l'utilisateur à chercher.
	 * @return optional user.
	 */
	Optional<UUser> findUserByEmail(String uEmail);

	/**
	 * renvoie un optional user à partir de son ID.
	 * 
	 * @param userId l'ID de l'utilisateur à chercher.
	 * @return optional user.
	 */
	Optional<UUser> findUserById( int userId);

	/**
	 * enregistrer un nouveau user.
	 * 
	 * @param userInfo l'utilisateur à enregistrer.
	 */
	void insertUser(UUser userInfo);

	/**
	 * Met à jour les informations d'un utilisateur.
	 * 
	 * @param user l'utilisateur à mettre à jour.
	 * @return Un indicateur de succès (par exemple, 1 pour succès, 0 pour échec).
	 */
	int updatePassword(UUser user);
}

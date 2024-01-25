package com.example.demo.service.implementation;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ContractDTO;
import com.example.demo.dto.mapper.ContractDtoMapper;
import com.example.demo.entity.Contract;
import com.example.demo.exception.DatabaseQueryFailureException;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.ContractMapper;
import com.example.demo.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	private final ContractDtoMapper contractDtoMapper;

	private final ContractMapper contractMapper;
	
	private static final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

	public ContractServiceImpl(ContractDtoMapper contractDtoMapper, ContractMapper contractMapper) {
		this.contractDtoMapper = contractDtoMapper;
		this.contractMapper = contractMapper;
	}


	/**
	 * Enregistre un contrat dans la base de données à partir du DTO fourni.
	 *
	 * @param contractDTO Le DTO du contrat à enregistrer.
	 * @return L'identifiant du contrat enregistré.
	 * @throws DatabaseQueryFailureException Si l'insertion du contrat dans la base de données échoue.
	 * @throws IllegalArgumentException Si le paramètre contractDTO est null.
	 *
	 * Cette méthode génère un numéro de contrat aléatoire, mappe le DTO vers l'entité Contract et tente d'insérer le contrat dans la base de données. Si l'insertion échoue ou si le DTO est null, une exception appropriée est lancée.
	 */
	@Override
	public int saveContract(ContractDTO contractDTO) throws DatabaseQueryFailureException {
		try {
			if (contractDTO == null) {
				log.error("Le paramètre contractDto ne peut être null");
				throw new IllegalArgumentException("Le paramètre contractDto ne peut être null");
			}
			
			contractDTO.setcContractNumber(generateRandomContractNumber());
			Contract contract = contractDtoMapper.toContract(contractDTO);
			int isContractInserted = contractMapper.insertContract(contract);
			
			if (isContractInserted == 0) {
				log.error("Échec de l'insertion du contrat dans la base de données");
	            throw new DatabaseQueryFailureException("Échec de l'insertion du contrat dans la base de données");
			}

			log.info("Contrat créé avec succès avec l'id = " + contract.getcId());
	        return contract.getcId();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (DatabaseQueryFailureException e) {
			throw e;
		}
	}

	public static String generateRandomContractNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 13).toUpperCase();
    }

	/**
	 * Récupère la liste des contrats associés à un identifiant de prestataire de services donné.
	 *
	 * @param serviceProviderId L'identifiant du prestataire de services.
	 * @return Liste des contrats associés au prestataire de services.
	 * @throws MessageModelNotFoundException Si aucun contrat n'existe pour cet identifiant.
	 *
	 * Cette méthode interroge la base de données pour obtenir les contrats liés à l'ID du prestataire de services et renvoie une liste de ces contrats. Si aucun contrat n'est trouvé, une exception est lancée.
	 */
	@Override
	public List<Contract> getContractsByServiceProviderId(Integer serviceProviderId) {
		System.err.println(serviceProviderId + " service");
		List<Contract> contracts = contractMapper.getContractsByServiceProviderId(serviceProviderId);
        return Optional.ofNullable(contracts).filter(not(List::isEmpty))
                .orElseThrow(() -> new MessageModelNotFoundException("No contract exists for this id!"));
	}

	/**
	 * Récupère la liste des contrats associés à un identifiant de sous-traitant donné.
	 *
	 * @param subContractorId L'identifiant du sous-traitant.
	 * @return Liste des contrats associés au sous-traitant.
	 * @throws MessageModelNotFoundException Si aucun contrat n'existe pour cet identifiant.
	 *
	 * Cette méthode interroge la base de données pour obtenir les contrats liés à l'ID du sous-traitant et renvoie une liste de ces contrats. Si aucun contrat n'est trouvé, une exception est lancée.
	 */
	@Override
	public List<Contract> getContractsBySubcontractorId(Integer subContractorId) {
		List<Contract> contracts = contractMapper.getContractsBySubcontractorId(subContractorId);
        return Optional.ofNullable(contracts).filter(not(List::isEmpty))
                .orElseThrow(() -> new MessageModelNotFoundException("No contract exists for this id!"));
	}
}

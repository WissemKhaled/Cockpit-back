package com.example.demo.service.implementation;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MessageModel;
import com.example.demo.exception.MessageModelNotFoundException;
import com.example.demo.mappers.MessageModelMapper;
import com.example.demo.service.MessageModelService;

@Service
public class MessageModelServiceImpl implements MessageModelService {

	private final MessageModelMapper messageModelMapper;

	public MessageModelServiceImpl(MessageModelMapper mapper) {
		this.messageModelMapper = mapper;
	}

	/**
	 * Récupère tous les modèles de messages associés à un identifiant de sous-traitant donné.
	 *
	 * @param subContractorId L'identifiant du sous-traitant pour lequel récupérer les modèles de messages.
	 * @return Une liste des instances de MessageModel associées à l'identifiant spécifié du sous-traitant.
	 * @throws MessageModelNotFoundException si aucune instance de MessageModel n'est trouvée pour l'identifiant de sous-traitant fourni.
	 *
	 * Cette méthode interroge la base de données pour les modèles de messages liés à l'identifiant du sous-traitant spécifié. Si aucun modèle de message n'est trouvé, une MessageModelNotFoundException est levée.
	 */

	@Override
	public List<MessageModel> getAllMessageModelBySubcontractorId(Integer subContractorId) {
		List<MessageModel> messageModels = messageModelMapper.getAllMessageModelBySubcontractorId(subContractorId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this id!"));
	}

	/**
	 * Récupère tous les modèles de messages associés à un identifiant de prestataire de services donné.
	 *
	 * @param serviceProviderId L'identifiant du prestataire de services pour lequel récupérer les modèles de messages.
	 * @return Une liste des instances de MessageModel associées à l'identifiant spécifié du prestataire de services.
	 * @throws MessageModelNotFoundException si aucune instance de MessageModel n'est trouvée pour l'identifiant du prestataire de services fourni.
	 *
	 * Cette méthode interroge la base de données pour les modèles de messages liés à l'identifiant du prestataire de services spécifié. Si aucun modèle de message n'est trouvé, une MessageModelNotFoundException est levée.
	 */
	@Override
	public List<MessageModel> getAllMessageModelByServiceProviderId(Integer serviceProviderId) {
		List<MessageModel> messageModels = messageModelMapper.getAllMessageModelByServiceProviderId(serviceProviderId);
		return Optional.ofNullable(messageModels).filter(not(List::isEmpty))
				.orElseThrow(() -> new MessageModelNotFoundException("No message model exists for this id!"));
	}
}

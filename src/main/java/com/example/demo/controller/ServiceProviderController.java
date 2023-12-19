package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.ServiceProviderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/service-providers")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceProviderController {

	private final ServiceProviderService serviceProviderService;
	private final ServiceProviderDtoMapper serviceProviderDtoMapper;
	
	public ServiceProviderController(ServiceProviderService serviceProviderService,
			ServiceProviderDtoMapper serviceProviderDtoMapper) {
		this.serviceProviderService = serviceProviderService;
		this.serviceProviderDtoMapper = serviceProviderDtoMapper;
	}

	@GetMapping("/{spId}")
	public ResponseEntity<ServiceProviderDto> getServiceProviderById(@PathVariable int spId) {
		try {
			return new ResponseEntity<>(serviceProviderDtoMapper.serviceProviderToDto(serviceProviderService.getServiceProviderById(spId)), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<ServiceProviderDto> saveServiceProvider(@Valid @RequestBody ServiceProviderDto serviceProviderDto) {
		try {
			serviceProviderDto.setSpFirstName(serviceProviderService.firstNameAndEmailFormatter(serviceProviderDto.getSpFirstName()));
			serviceProviderDto.setSpName(serviceProviderService.nameFormatter(serviceProviderDto.getSpName()));
			serviceProviderDto.setSpEmail(serviceProviderService.firstNameAndEmailFormatter(serviceProviderDto.getSpEmail()));
			boolean isServiceProviderExist = serviceProviderService.checkIfServiceProviderExistById(serviceProviderDto.getSpId());
			if (isServiceProviderExist) {
				serviceProviderService.handleServiceProviderUpdating(serviceProviderDto);
				int updateServiceProviderId = serviceProviderService.updateServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDto));
				ServiceProvider updatedServiceProvider = serviceProviderService.getServiceProviderById(updateServiceProviderId);
				return new ResponseEntity<>(
						serviceProviderDtoMapper.serviceProviderToDto(updatedServiceProvider), HttpStatus.OK);
			} else {
				if (serviceProviderDto.getSpId() > 0) {
					serviceProviderService.handleServiceProviderSaving(serviceProviderDto);
					int savedServiceProviderId = serviceProviderService.saveServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDto));
					return new ResponseEntity<>(serviceProviderDtoMapper.serviceProviderToDto(serviceProviderService.getServiceProviderById(savedServiceProviderId)),HttpStatus.CREATED);
				} else {
					return new ResponseEntity("Invalid Id", HttpStatus.BAD_REQUEST);
				}
			}
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (EntityDuplicateDataException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/archive/{serviceProviderId}")
	public ResponseEntity<String> archiveServiceProvider(@PathVariable int serviceProviderId) {
		try {
		ServiceProvider serviceProviderToArchive = serviceProviderService.getServiceProviderById(serviceProviderId);
			if (serviceProviderToArchive.getSpStatus().getStId() == 4) {
				throw new AlreadyArchivedEntity(String.format("Erreur: le prestataire avec l'id %d est déjà archivé.", serviceProviderId));
			}
			serviceProviderService.archiveServiceProvider(serviceProviderToArchive);
			return new ResponseEntity<>(String.format("Le prestataire avec l'id: %d a été archivé avec succées", serviceProviderId), HttpStatus.OK);
		} catch (AlreadyArchivedEntity e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all-service-providers/{subcontractorId}")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersBySubcontractorId(@PathVariable int subcontractorId) {
		try {
			List<ServiceProviderDto> serviceProviders= serviceProviderService.getServiceProvidersBySubcontractorId(subcontractorId).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
			if (serviceProviders.isEmpty()) throw new EntityNotFoundException(String.format("Le sous-traitant avec l'id: %d n'a pas de prestataires", subcontractorId));
			return new ResponseEntity<>(serviceProviders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all-service-providers-with-or-without-status")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersWithOrWithoutStatus(
			@RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize,
			@RequestParam(name = "statusId", required = false) int statusId
			) {
		return new ResponseEntity<>(serviceProviderService.getAllServiceProvidersFiltredByStatus(sortingMethod, pageNumber, pageSize, statusId)
				.stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList(), HttpStatus.OK);    
	}
	
	@GetMapping("/count-all-service-providers-with-or-without-status")
	public ResponseEntity<Integer> countAllServiceProvidersFiltredByStatus(@RequestParam int statusId) {
		try {
				return new ResponseEntity<>(serviceProviderService.countAllServiceProvidersFiltredByStatus(statusId), HttpStatus.OK);    			
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all-service-providers/search")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersBySearchAndStatus(
			@RequestParam(name = "searchTerms") String searchTerms,
			@RequestParam(name = "sortingMethod", defaultValue = "asc", required = false) String sortingMethod,
			@RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize,
			@RequestParam(name = "statusId") int statusId,
			@RequestParam(name = "searchAttribute") String searchAttribute) {
		try {
			List<ServiceProviderDto> filtredServiceProviders= serviceProviderService.getAllServiceProvidersBySearchAndWithOrWithoutStatusFiltring(searchTerms, pageNumber, pageSize,statusId,searchAttribute).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
			if (filtredServiceProviders.isEmpty()) throw new EntityNotFoundException(String.format("Le prestataire avec le nom: %s n'existe pas", searchTerms));
			return new ResponseEntity<>(filtredServiceProviders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/count-all-service-providers/search")
	public ResponseEntity<Integer> getNumberOfServiceProvidersBySearchAndStatus(
			@RequestParam(name = "searchTerms") String searchTerms,
			@RequestParam(name = "statusId") int statusId,
			@RequestParam(name = "searchAttribute") String searchAttribute) {
		try {
			Integer numberOfServiceProviders= serviceProviderService.getNumberOfServiceProvidersBySearchAndWithOrWithoutStatusFiltring(searchTerms,statusId, searchAttribute);
			if (numberOfServiceProviders == 0) throw new EntityNotFoundException(String.format("Le prestataire avec le nom: %s et le statusId: %d n'existe pas", searchTerms, statusId));
			return new ResponseEntity<>(numberOfServiceProviders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

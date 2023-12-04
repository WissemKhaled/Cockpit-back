package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.example.demo.dto.SubcontractorDto;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.AlreadyArchivedEntity;
import com.example.demo.exception.EntityDuplicateDataException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.ServiceProviderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/service-providers")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceProviderController {

	private final ServiceProviderService serviceProviderService;
	private final ServiceProviderDtoMapper serviceProviderDtoMapper;

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
	
	@GetMapping("/all-service-providers")
	public ResponseEntity<List<ServiceProviderDto>> getAllServiceProviders() {
		try {
			List<ServiceProviderDto> serviceProviders = serviceProviderService.getAllServiceProviders().stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList();
			if (serviceProviders.isEmpty()) throw new EntityNotFoundException("Il n'y a pas de prestataires enregistrés.");
			return new ResponseEntity<>(serviceProviders, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all-non-archived-service-providers")
	public ResponseEntity<List<ServiceProviderDto>> getAllNonArchivedServiceProviders(
			@RequestParam(name = "selectedStatusId", defaultValue = "s_fk_status_id", required = false) String selectedStatusId,
			@RequestParam(name = "sorting", defaultValue = "asc", required = false) String sorting,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
	) {
		try {
			return new ResponseEntity<>(serviceProviderService.getAllNonArchivedServiceProviders(selectedStatusId, sorting, page, pageSize).stream().map(serviceProviderDtoMapper::serviceProviderToDto).toList(), HttpStatus.OK);    
		} catch (RuntimeException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ServiceProviderDto;
import com.example.demo.dto.mapper.ServiceProviderDtoMapper;
import com.example.demo.entity.ServiceProvider;
import com.example.demo.exception.AlreadyArchivedSubcontractor;
import com.example.demo.exception.ServiceProviderNotFoundException;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.ServiceProviderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/service-providers")
public class ServiceProviderController {

	private final ServiceProviderService serviceProviderService;
	private final ServiceProviderDtoMapper serviceProviderDtoMapper;

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtServiceImplementation jwtService;

	@GetMapping("/{spId}")
	public ResponseEntity<ServiceProviderDto> getServiceProviderById(@PathVariable int spId,
			HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				String email = jwtService.extractUsername(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				if (jwtService.validateToken(token, userDetails)) {
					return new ResponseEntity<>(serviceProviderDtoMapper
							.serviceProviderToDto(serviceProviderService.getServiceProviderById(spId)), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (ServiceProviderNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<ServiceProviderDto> saveServiceProvider(@RequestBody ServiceProviderDto serviceProviderDto,
			HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				String email = jwtService.extractUsername(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				if (jwtService.validateToken(token, userDetails)) {
					boolean isSubcontractorExist = serviceProviderService
							.checkIfServiceProviderExist(serviceProviderDto.getSpId());
					if (isSubcontractorExist) {
						int updateServiceProviderId = serviceProviderService.updateServiceProvider(serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDto));
						ServiceProvider updatedServiceProvider = serviceProviderService.getServiceProviderById(updateServiceProviderId);
						return new ResponseEntity<ServiceProviderDto>(serviceProviderDtoMapper.serviceProviderToDto(updatedServiceProvider),HttpStatus.OK);
					} else {
						if (serviceProviderDto.getSpId() > 0) {
							int saveServiceProvider = serviceProviderService.saveServiceProvider(
									serviceProviderDtoMapper.dtoToserviceProvider(serviceProviderDto));
							return new ResponseEntity<>(
									serviceProviderDtoMapper.serviceProviderToDto(
											serviceProviderService.getServiceProviderById(saveServiceProvider)),
									HttpStatus.CREATED);
						} else {
							return new ResponseEntity("Invalid Id", HttpStatus.BAD_REQUEST);
						}
					}
				} else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (ServiceProviderNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/archive/{serviceProviderId}")
	public ResponseEntity<?> archiveServiceProvider(@PathVariable int serviceProviderId) {
		try {
			ServiceProvider serviceProviderToArchive = serviceProviderService.getServiceProviderById(serviceProviderId);
			if (serviceProviderToArchive.getSpStatus().getStId() == 4) throw new AlreadyArchivedSubcontractor(String.format("le prestataire avec l'id %d est déjà archivé.", serviceProviderId));
			serviceProviderService.archiveServiceProvider(serviceProviderToArchive);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch (AlreadyArchivedSubcontractor e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
}

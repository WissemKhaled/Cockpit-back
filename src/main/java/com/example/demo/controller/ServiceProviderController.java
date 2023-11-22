package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ServiceProviderController {
//	
//	private final ServiceProviderService serviceProviderService;
//	
//	@Autowired
//	@Qualifier("userDetailsService")
//	private UserDetailsService userDetailsService;
//
//	@Autowired
//	private JwtServiceImplementation jwtService;
//	
//	public ResponseEntity<ServiceProviderDto> getServiceProviderById(int spId, HttpServletRequest request) {
////		String authorizationHeader = request.getHeader("Authorization");
////		try {
////			 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
////				 String token = authorizationHeader.substring(7);
////				 String email = jwtService.extractUsername(token);
////				 UserDetails userDetails = userDetailsService.loadUserByUsername(email);
////				 if (jwtService.validateToken(token, userDetails)) {
////		            	return new ResponseEntity<>(serviceProviderService.)
////		            } else {
////		                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
////		            }
////			 } else {
////				 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
////			 }
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
//		return new ResponseEntity<ServiceProviderDto>(HttpStatus.OK);
//	}
}

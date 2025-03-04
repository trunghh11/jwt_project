package com.example.securingweb;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;

	public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return jwtTokenUtil.generateToken(userDetails.getUsername());
		} catch (AuthenticationException e) {
			throw new RuntimeException("Invalid login credentials");
		}
	}

	@GetMapping("/roles")
	public List<String> getUserRoles(@RequestParam String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}
}
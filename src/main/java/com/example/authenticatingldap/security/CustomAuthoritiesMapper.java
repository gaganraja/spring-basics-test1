package com.example.authenticatingldap.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

public class CustomAuthoritiesMapper implements GrantedAuthoritiesMapper{

	@Override
	public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
                .map(grantedAuthority -> {
                    String role = grantedAuthority.getAuthority();
                    switch (role) {
                        case "ROLE_DEVELOPERS":
                            return new SimpleGrantedAuthority("ROLE_DEVELOPER");
                        case "ROLE_MANAGERS":
                            return new SimpleGrantedAuthority("ROLE_MANAGER");
                        default:
                            return new SimpleGrantedAuthority(role);
                    }
                })
                .collect(Collectors.toList());
	}

}

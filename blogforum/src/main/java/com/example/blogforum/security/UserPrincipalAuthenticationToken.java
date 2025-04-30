package com.example.blogforum.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
	private final UserPrincipal principal;

	public UserPrincipalAuthenticationToken(UserPrincipal principal) {
		super(principal.getAuthorities());
		this.principal = principal;
		setAuthenticated(true);
	}
	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPrincipal getPrincipal() {
		return principal;
	}

}

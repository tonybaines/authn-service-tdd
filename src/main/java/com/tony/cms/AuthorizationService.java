package com.tony.cms;

public class AuthorizationService {
    private final Resources resources;
    private final AuthenticationService authnService;

    public AuthorizationService(Resources resources, AuthenticationService authnService) {
        this.resources = resources;
        this.authnService = authnService;
    }

    public boolean isAllowed(User user, Resource resource) {
        if (user == null || resource == null) {
            throw new IllegalArgumentException("Null parameter values are not allowed");
        }
        if (resources.contains(resource)) {
            if (authnService.isAuthenticated(user)) {
                return true;
            }
            return false;
        }
        else /*unprotected resource*/{
            return true;
        }
    }
}

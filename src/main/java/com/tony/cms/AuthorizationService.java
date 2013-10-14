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

        if (isUnprotected(resource)) return true;
        if (unauthenticated(user)) return false;
        if (resource.allowedFor(user)) return true;

        return false;

    }

    private boolean unauthenticated(User user) {return !authnService.isAuthenticated(user);}

    private boolean isUnprotected(Resource resource) {return !resources.contains(resource);}
}

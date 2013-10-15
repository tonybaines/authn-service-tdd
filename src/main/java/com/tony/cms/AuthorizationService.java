package com.tony.cms;

public class AuthorizationService {
    private final Resources resources;
    private final AuthenticationService authnService;

    public AuthorizationService(Resources resources, AuthenticationService authnService) {
        this.resources = resources;
        this.authnService = authnService;
    }

    public AuthorizationResponse isAllowed(User user, Resource resource) {
        if (user == null || resource == null) {
            throw new IllegalArgumentException("Null parameter values are not allowed");
        }

        if (isUnprotected(resource))
            return new AuthorizationResponse(true);
        if (unauthenticated(user))
            return new AuthorizationResponse(false);

        AuthorizationResponse userAllowedResponse = resource.allowedFor(user);
        if (userAllowedResponse.allowed()) {
            return new AuthorizationResponse(true);
        }
        else {
            return userAllowedResponse;
        }
    }

    private boolean unauthenticated(User user) {
        return !authnService.isAuthenticated(user);
    }

    private boolean isUnprotected(Resource resource) {
        return !resources.contains(resource);
    }
}

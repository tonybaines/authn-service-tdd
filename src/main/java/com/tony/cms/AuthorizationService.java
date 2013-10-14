package com.tony.cms;

public class AuthorizationService {
    private final Resources resources;

    public AuthorizationService(Resources resources) {
        this.resources = resources;
    }

    public boolean isAllowed(User user, Resource resource) {
        if (user == null || resource == null) {
            throw new IllegalArgumentException("Null parameter values are not allowed");
        }
        return !(resources.contains(resource));
    }
}

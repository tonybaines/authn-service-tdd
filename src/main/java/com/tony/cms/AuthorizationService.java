package com.tony.cms;

public class AuthorizationService {
    private final Resources resources;

    public AuthorizationService(Resources resources) {
        this.resources = resources;
    }

    public boolean isAllowed(User user, Resource resource) {
        return !(resources.contains(resource));
    }
}

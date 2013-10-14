package com.tony.cms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Resource {
    private final String resourcePath;
    private final Set<User> allowedUsers;

    public Resource(String resourcePath, User ... allowedUsers) {
        this.resourcePath = resourcePath;
        this.allowedUsers = new HashSet<User>(Arrays.asList(allowedUsers));
    }

    public boolean allowedForUser(User user) {
        return allowedUsers.contains(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (!resourcePath.equals(resource.resourcePath)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return resourcePath.hashCode();
    }

    @Override
    public String toString() {
        return "Resource{" +
            "resourcePath='" + resourcePath + '\'' +
            ", allowedUsers=" + allowedUsers +
            '}';
    }
}

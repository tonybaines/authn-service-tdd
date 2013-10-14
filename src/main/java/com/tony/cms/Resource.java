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
}

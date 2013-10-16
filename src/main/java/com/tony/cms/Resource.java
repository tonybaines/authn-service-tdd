package com.tony.cms;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Resource {
    private final String resourcePath;
    private final Set<User> allowedUsers;
    private final Set<Group> allowedGroups;

    public Resource(String resourcePath, User ... allowedUsers) {
        this.resourcePath = resourcePath;
        this.allowedUsers = new HashSet<User>(Arrays.asList(allowedUsers));
        this.allowedGroups = Collections.emptySet();
    }

    public Resource(String resourcePath, Group... allowedGroups) {
        this.resourcePath = resourcePath;
        this.allowedGroups = new HashSet<Group>(Arrays.asList(allowedGroups));
        this.allowedUsers = Collections.emptySet();
    }

    public AuthorizationResponse allowedFor(User user) {
        if (isAllowedIndividually(user)) {
            return new AuthorizationResponse(true);
        }
        else {
            return isInAnAllowedGroup(user);
        }
    }

    private boolean isAllowedIndividually(User user) {
        return allowedUsers.contains(user);
    }

    private AuthorizationResponse isInAnAllowedGroup(User user) {
        for (Group allowedGroup : allowedGroups) {
            if (user.isAMemberOf(allowedGroup)) return new AuthorizationResponse(true);
        }

        if (!allowedGroups.isEmpty()) {
            StringBuilder reason = new StringBuilder("Access is available for members of groups: ['");
            for (Group allowedGroup : allowedGroups) {
                reason.append(allowedGroup.toString()+",");
            }
            reason.deleteCharAt(reason.lastIndexOf(","));
            reason.append("']");
            return new AuthorizationResponse(false, reason.toString());
        }
        return new AuthorizationResponse(false);
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

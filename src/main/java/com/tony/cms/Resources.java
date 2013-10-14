package com.tony.cms;

import java.util.HashSet;
import java.util.Set;

public class Resources {
    private Set<Resource> resources = new HashSet<Resource>();

    public void addResource(Resource protectedResource) {
        this.resources.add(protectedResource);
    }

    public boolean contains(Resource resource) {
        return resources.contains(resource);
    }
}

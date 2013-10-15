package com.tony.cms;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private final String name;
    private Set<User> members = new HashSet<User>();

    public Group(String name) {
        this.name = name;
    }

    public void addUser(User member) {
        members.add(member);
    }

    public boolean includes(User user) {
        return members.contains(user);
    }

    @Override
    public String toString() {
        return name;
    }
}

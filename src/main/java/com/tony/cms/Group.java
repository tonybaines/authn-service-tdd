package com.tony.cms;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private Set<User> members = new HashSet<User>();
    public void addUser(User member) {
        members.add(member);
    }

    public boolean includes(User user) {
        return members.contains(user);
    }
}

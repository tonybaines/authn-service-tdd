package com.tony.cms;

public class User {
    public static final User ANONYMOUS = new User(null);
    private final String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public boolean isAMemberOf(Group group) {
        return group.includes(this);
    }
}

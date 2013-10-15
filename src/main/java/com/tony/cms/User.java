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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId='" + userId + '\'' +
            '}';
    }
}

package com.tony.cms;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class UserTest {
    @Test
    public void shouldIndicateWhetherAUserIsAMemberOfAGroup() throws Exception {
        final User user = new User("foo");
        Group group = new Group("group-bar") {{
            addUser(user);
        }};
        assertThat(user.isAMemberOf(group), is(true));
    }

    @Test
    public void instancesWithTheSameUserIdAreConsideredEqual() throws Exception {
        assertThat(new User("foo"), is(new User("foo")));
    }

    @Test
    public void instancesWithDifferentUserIdsAreNotEqual() throws Exception {
        assertThat(new User("foo"), is(not((new User("bar")))));
    }
}

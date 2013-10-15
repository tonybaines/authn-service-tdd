package com.tony.cms;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class UserTest {
    @Test
    public void shouldIndicateWhetherAUserIsAMemberOfAGroup() throws Exception {
        final User user = new User("foo");
        Group group = new Group() {{
            addUser(user);
        }};
        assertThat(user.isAMemberOf(group), is(true));
    }
}

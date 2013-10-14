package com.tony.cms;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AuthorizationServiceTest {
    private User anonUser = User.ANONYMOUS;
    private Resource unprotectedResource = new Resource("/unprotected/thing");

    @Test
    public void shouldAcceptRequestsFromAnAnonymousUserToAnUnprotectedResource() throws Exception {
        AuthorizationService authService = new AuthorizationService();
        assertThat(authService.isAllowed(anonUser, unprotectedResource), is(true));
    }
}

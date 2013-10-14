package com.tony.cms;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AuthorizationServiceTest {
    private User anonUser = User.ANONYMOUS;
    private Resource unprotectedResource = new Resource("/unprotected/thing");
    private Resource protectedResource = new Resource("/protected/thing");
    private AuthorizationService authService;
    private Resources protectedResources = new Resources();

    @Before
    public void setUp() throws Exception {
        protectedResources.addResource(protectedResource);
        authService = new AuthorizationService(protectedResources);
    }

    @Test
    public void shouldAcceptRequestsFromAnAnonymousUserToAnUnprotectedResource() throws Exception {
        assertThat(authService.isAllowed(anonUser, unprotectedResource), is(true));
    }

    @Test
    public void shouldRejectRequestsFromAnAnonymousUserToAProtectedResource() throws Exception {
        assertThat(authService.isAllowed(anonUser, protectedResource), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfAnInvalidUserIsSupplied() throws Exception {
        authService.isAllowed(null, unprotectedResource);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfAnInvalidResourceIsSupplied() throws Exception {
        authService.isAllowed(anonUser, null);
    }
}

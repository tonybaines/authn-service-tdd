package com.tony.cms;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTest {
    private User anonUser = User.ANONYMOUS;
    private User authenticatedUser1 = new User("authenticatedUser1");
    private Resource unprotectedResource = new Resource("/unprotected/thing");
    private Resource protectedResource = new Resource("/protected/thing");
    private AuthorizationService authzService;
    private Resources protectedResources = new Resources();
    private AuthenticationService authnService;

    @Before
    public void setUp() throws Exception {
        protectedResources.addResource(protectedResource);

        authnService = Mockito.mock(AuthenticationService.class);
        when(authnService.isAuthenticated(authenticatedUser1)).thenReturn(true);

        authzService = new AuthorizationService(protectedResources, authnService);
    }

    @Test
    public void shouldAcceptRequestsFromAnAnonymousUserToAnUnprotectedResource() throws Exception {
        assertThat(authzService.isAllowed(anonUser, unprotectedResource), is(true));
    }

    @Test
    public void shouldRejectRequestsFromAnAnonymousUserToAProtectedResource() throws Exception {
        assertThat(authzService.isAllowed(anonUser, protectedResource), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfAnInvalidUserIsSupplied() throws Exception {
        authzService.isAllowed(null, unprotectedResource);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfAnInvalidResourceIsSupplied() throws Exception {
        authzService.isAllowed(anonUser, null);
    }

    @Test
    public void shouldAcceptRequestsFromAnAuthenticatedUserToAProtectedResourceWithoutExtraRestrictions() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1, protectedResource), is(true));
        verify(authnService).isAuthenticated(authenticatedUser1);
    }
}

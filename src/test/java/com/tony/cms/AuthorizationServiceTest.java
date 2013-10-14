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
    private User authenticatedUser2 = new User("authenticatedUser2");
    private Resource unprotectedResource = new Resource("/unprotected/thing", authenticatedUser1);
    private Resource protectedResourceUser1Allowed = new Resource("/protected/thing", authenticatedUser1);
    private Resource protectedResourceUser2Allowed = new Resource("/protected/other/thing", authenticatedUser2);
    private AuthorizationService authzService;
    private Resources protectedResources = new Resources();
    private AuthenticationService authnService;

    @Before
    public void setUp() throws Exception {
        protectedResources.addResource(protectedResourceUser1Allowed);
        protectedResources.addResource(protectedResourceUser2Allowed);

        authnService = Mockito.mock(AuthenticationService.class);
        when(authnService.isAuthenticated(authenticatedUser1)).thenReturn(true);
        when(authnService.isAuthenticated(authenticatedUser2)).thenReturn(true);

        authzService = new AuthorizationService(protectedResources, authnService);
    }

    @Test
    public void shouldAcceptRequestsFromAnAnonymousUserToAnUnprotectedResource() throws Exception {
        assertThat(authzService.isAllowed(anonUser, unprotectedResource), is(true));
    }

    @Test
    public void shouldRejectRequestsFromAnAnonymousUserToAProtectedResource() throws Exception {
        assertThat(authzService.isAllowed(anonUser, protectedResourceUser1Allowed), is(false));
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
        assertThat(authzService.isAllowed(authenticatedUser1, protectedResourceUser1Allowed), is(true));
        verify(authnService).isAuthenticated(authenticatedUser1);
    }

    @Test
    public void shouldAllowAccessFromAnAuthenticatedUserToAProtectedResourceWhereTheyAreExplicitlyNamed() throws Exception {
        Resource resource = new Resource("/protected/other/thing", authenticatedUser1);
        assertThat(authzService.isAllowed(authenticatedUser1, resource), is(true));
    }

    @Test
    public void shouldRejectAccessFromAnAuthenticatedUserToAResourceWithExplicitPermissionsWhereTheyAreNotNamed() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1, protectedResourceUser2Allowed), is(false));
    }
}

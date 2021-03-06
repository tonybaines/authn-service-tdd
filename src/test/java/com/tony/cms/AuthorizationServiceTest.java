package com.tony.cms;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.tony.cms.AuthorizationServiceTest.Fixture.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTest {
    private User anonUser = User.ANONYMOUS;

    private AuthorizationService authzService;
    private Resources protectedResources = new Resources();
    private AuthenticationService authnService;

    @Before
    public void setUp() throws Exception {
        protectedResources.addResource(protectedResourceUser1Allowed());
        protectedResources.addResource(protectedResourceUser2Allowed());
        protectedResources.addResource(protectedResourceUsers1and2Allowed());
        protectedResources.addResource(protectedResourceGroup1Allowed());
        protectedResources.addResource(protectedResourceGroup2Allowed());
        protectedResources.addResource(protectedResourceGroups1And2Allowed());

        authnService = Mockito.mock(AuthenticationService.class);
        when(authnService.isAuthenticated(authenticatedUser1())).thenReturn(true);
        when(authnService.isAuthenticated(authenticatedUser2())).thenReturn(true);
        when(authnService.isAuthenticated(authenticatedUser3())).thenReturn(true);

        authzService = new AuthorizationService(protectedResources, authnService);
    }

    @Test
    public void shouldAcceptRequestsFromAnAnonymousUserToAnUnprotectedResource() throws Exception {
        assertThat(authzService.isAllowed(anonUser, unprotectedResource()).allowed(), is(true));
    }

    @Test
    public void shouldRejectRequestsFromAnAnonymousUserToAProtectedResource() throws Exception {
        assertThat(authzService.isAllowed(anonUser, protectedResourceUser1Allowed()).allowed(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfAnInvalidUserIsSupplied() throws Exception {
        authzService.isAllowed(null, unprotectedResource()).allowed();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionIfAnInvalidResourceIsSupplied() throws Exception {
        authzService.isAllowed(anonUser, null).allowed();
    }

    @Test
    public void shouldAcceptRequestsFromAnAuthenticatedUserToAProtectedResourceWithoutExtraRestrictions() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1(), protectedResourceUser1Allowed()).allowed(), is(true));
        verify(authnService).isAuthenticated(authenticatedUser1());
    }

    @Test
    public void shouldAllowAccessFromAnAuthenticatedUserToAProtectedResourceWhereTheyAreExplicitlyNamed() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1(), protectedResourceUser1Allowed()).allowed(), is(true));
    }

    @Test
    public void shouldRejectAccessFromAnAuthenticatedUserToAResourceWithExplicitPermissionsWhereTheyAreNotNamed() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1(), protectedResourceUser2Allowed()).allowed(), is(false));
    }

    @Test
    public void shouldAllowAccessFromAnAuthenticatedUserToAProtectedResourceWhereTheyAreExplicitlyNamedAsOneOfMany() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1(), protectedResourceUsers1and2Allowed()).allowed(), is(true));
        assertThat(authzService.isAllowed(authenticatedUser2(), protectedResourceUsers1and2Allowed()).allowed(), is(true));
    }

    @Test
    public void shouldAllowAccessFromAnAuthenticatedUserToAProtectedResourceWhereTheyAreAMemberOfANamedGroup() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser1(), protectedResourceGroup1Allowed()).allowed(), is(true));
        assertThat(authzService.isAllowed(authenticatedUser1(), protectedResourceGroup2Allowed()).allowed(), is(true));
        assertThat(authzService.isAllowed(authenticatedUser2(), protectedResourceGroup2Allowed()).allowed(), is(true));
    }

    @Test
    public void shouldNotAllowAccessFromAnAuthenticatedUserToAProtectedResourceWhereTheyAreANotMemberOfANamedGroup() throws Exception {
        assertThat(authzService.isAllowed(authenticatedUser2(), protectedResourceGroup1Allowed()).allowed(), is(false));
    }

    @Test
    public void shouldGetAListOfAllowedGroupsWhenAuthorizationFailsForAnAuthenticatedUserAndGroupsAreDefinedForTheResource() throws Exception {
        AuthorizationResponse authzResponse = authzService.isAllowed(authenticatedUser3(), protectedResourceGroups1And2Allowed());
        assertThat(authzResponse.allowed(), is(false));
        assertThat(authzResponse.reason(), is("Access is available for members of groups: ['group-1,group-2']"));
    }

    public static class Fixture {
        public static User authenticatedUser1() {
            return new User("authenticatedUser1");
        }

        public static User authenticatedUser2() {
            return new User("authenticatedUser2");
        }

        public static User authenticatedUser3() {
            return new User("authenticatedUser3");
        }

        public static Group group1() {
            return new Group("group-1") {
                {
                    addUser(authenticatedUser1());
                }
            };
        }

        public static Group group2() {
            return new Group("group-2") {
                {
                    addUser(authenticatedUser1());
                    addUser(authenticatedUser2());
                }
            };
        }

        public static Resource unprotectedResource() {
            return new Resource("/unprotected/thing", authenticatedUser1());
        }

        public static Resource protectedResourceUser1Allowed() {
            return new Resource("/protected/thing", authenticatedUser1());
        }

        public static Resource protectedResourceUser2Allowed() {
            return new Resource("/protected/other/thing", authenticatedUser2());
        }

        public static Resource protectedResourceUsers1and2Allowed() {
            return new Resource("/protected/another/thing", authenticatedUser1(), authenticatedUser2());
        }

        public static Resource protectedResourceGroup1Allowed() {
            return new Resource("/protected/thing/by-group", group1());
        }

        public static Resource protectedResourceGroup2Allowed() {
            return new Resource("/protected/thing/by-group/2", group2());
        }

        public static Resource protectedResourceGroups1And2Allowed() {
            return new Resource("/protected/thing/by-groups/1/2", group1(), group2());
        }
    }
}

package com.tony.cms;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResourceTest {
    @Test
    public void instancesWithTheSamePathAreConsideredEqual() throws Exception {
        assertThat(new Resource("/path", User.ANONYMOUS), is(new Resource("/path")));
    }
}

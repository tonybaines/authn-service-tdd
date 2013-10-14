package com.tony.cms;

import org.junit.Test;

public class ResourcesTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowTwoResourcesToBeAddedWithTheSamePath() throws Exception {
        new Resources() {{
            addResource(new Resource("/path"));
            addResource(new Resource("/path"));
        }};
    }
}

package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Test;

public class OrganisationTest {

    @Test
    public void createOrganisationTest() {
        Organisation organisation = new Organisation(80.9, "test", "test.com");
        Assert.assertNotNull(organisation);
        Assert.assertEquals(80.9, organisation.getID(), 0);
        Assert.assertEquals("test", organisation.getName());
        Assert.assertEquals("test.com", organisation.getUrl());
    }
}

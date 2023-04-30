package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {

    @Test
    public void createLocationTest() {
        Location location = new Location(00,00);
        Assert.assertNotNull(location);
        Assert.assertEquals(00, location.getLat(), 0);
        Assert.assertEquals(00, location.getLon(), 0);
    }
}

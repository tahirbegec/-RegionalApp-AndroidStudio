package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Test;

public class AddressTest {

    @Test
    public void createAddressTest() {
        Address address = new Address("Straße", "Stadt", "55555");
        Assert.assertNotNull(address);
        Assert.assertEquals("Straße", address.getStreet());
        Assert.assertEquals("Stadt", address.getCity());
        Assert.assertEquals("55555", address.getZip());
    }
}

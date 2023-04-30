package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    @Test
    public void createMessageTest() {
        Message message = new Message("Hallo", "20.02.2021");
        Assert.assertNotNull(message);
        Assert.assertEquals("Hallo", message.getContent());
        Assert.assertEquals("20.02.2021", message.getDate());
    }
}

package io.github.msumsh.tests;

import io.github.msumsh.util.IpValidator;
import io.github.msumsh.model.DomainAddress;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IpValidatorTest {
    private IpValidator validator;

    @BeforeMethod
    public void setUp() {
        validator = new IpValidator();
    }

    @DataProvider(name = "validIps")
    public Object[][] validIps() {
        return new Object[][]{
                {"0.0.0.0"},
                {"127.0.0.1"},
                {"192.168.0.1"},
                {"255.255.255.255"},
                {"8.8.8.8"}
        };
    }

    @DataProvider(name = "invalidIps")
    public Object[][] invalidIps() {
        return new Object[][]{
                {""},
                {"256.1.1.1"},
                {"192.168.1"},
                {"192.168.1.1.1"},
                {"abc.def.ghi.jkl"},
                {"192.168.-1.1"},
                {"300.300.300.300"},
                {"1..1.1"},
                {" "}
        };
    }

    @Test(dataProvider = "validIps")
    public void validateShouldReturnTrueForValidIpv4(String ip) {
        Assert.assertTrue(validator.validate(ip));
    }

    @Test(dataProvider = "invalidIps")
    public void validateShouldReturnFalseForInvalidIpv4(String ip) {
        Assert.assertFalse(validator.validate(ip));
    }
}
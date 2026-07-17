package io.github.msumsh.tests;

import io.github.msumsh.model.DomainAddress;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DomainServiceTest extends BaseTest {

    @Test
    public void shouldAddNewPair() throws Exception {
        service.add("test.com", "1.1.1.1");

        List<DomainAddress> result = service.getAll();

        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getDomain(), "test.com");
        Assert.assertEquals(result.get(0).getIp(), "1.1.1.1");
    }

    @Test
    public void shouldSortByDomain() throws Exception {
        service.add("c-test.ru", "1.1.1.1");
        service.add("a-test.com", "2.2.2.2");
        service.add("b-test.com", "3.3.3.3");

        List<DomainAddress> result = service.getAll();

        Assert.assertEquals(result.get(0).getDomain(), "a-test.com");
        Assert.assertEquals(result.get(1).getDomain(), "b-test.com");
        Assert.assertEquals(result.get(2).getDomain(), "c-test.ru");
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Address with this domain already exists"
    )
    public void shouldNotAddDuplicateDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        service.add("test.com", "2.2.2.2");
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Address with this IP-address already exists"
    )
    public void shouldNotAddDuplicateIp() throws Exception {
        service.add("a-test.com", "1.1.1.1");

        service.add("b-test.com", "1.1.1.1");
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Invalid IPv4 address"
    )
    public void shouldNotAddInvalidIp() throws Exception {
        service.add("test.com", "999.999.999.999");

    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Domain can't be empty"
    )
    public void shouldNotAddEmptyDomain() throws Exception {
        service.add("", "1.1.1.1");

    }

    @Test
    public void shouldFindByDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        DomainAddress address = service.findByDomain("test.com");

        Assert.assertNotNull(address);
        Assert.assertEquals(address.getIp(), "1.1.1.1");
    }

    @Test
    public void shouldNotFindByNotExistentDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        DomainAddress address = service.findByDomain("domain.com");

        Assert.assertNull(address);
    }

    @Test
    public void shouldNotFindByEmptyDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        DomainAddress address = service.findByDomain("");

        Assert.assertNull(address);
    }

    @Test
    public void shouldFindByIp() throws Exception {
        service.add("test.com", "1.1.1.1");

        DomainAddress address = service.findByIp("1.1.1.1");

        Assert.assertNotNull(address);
        Assert.assertEquals(address.getDomain(), "test.com");
    }

    @Test
    public void shouldNotFindByNotExistentIp() throws Exception {
        service.add("test.com", "1.1.1.1");

        DomainAddress address = service.findByIp("2.2.2.2");

        Assert.assertNull(address);
    }

    @Test
    public void shouldNotFindByEmptyIp() throws Exception {
        service.add("test.com", "1.1.1.1");

        DomainAddress address = service.findByIp("");

        Assert.assertNull(address);
    }

    @Test
    public void shouldDeleteByDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        service.deleteByDomain("test.com");

        Assert.assertTrue(service.getAll().isEmpty());
    }

    @Test
    public void shouldNotDeleteByNotExistentDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        service.deleteByDomain("domain.com");

        Assert.assertFalse(service.getAll().isEmpty());
    }

    @Test
    public void shouldNotDeleteByEmptyDomain() throws Exception {
        service.add("test.com", "1.1.1.1");

        service.deleteByDomain("");

        Assert.assertFalse(service.getAll().isEmpty());
    }

    @Test
    public void shouldDeleteByIp() throws Exception {

        service.add("test.com", "1.1.1.1");

        service.deleteByIp("1.1.1.1");

        Assert.assertTrue(service.getAll().isEmpty());
    }

    @Test
    public void shouldDeleteByNotExistentIp() throws Exception {

        service.add("test.com", "1.1.1.1");

        service.deleteByIp("2.2.2.2");

        Assert.assertFalse(service.getAll().isEmpty());
    }

    @Test
    public void shouldDeleteByEmptyIp() throws Exception {

        service.add("test.com", "1.1.1.1");

        service.deleteByIp("");

        Assert.assertFalse(service.getAll().isEmpty());
    }
}
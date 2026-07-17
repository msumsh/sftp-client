package io.github.msumsh.tests;

import io.github.msumsh.service.DomainService;
import io.github.msumsh.util.IpValidator;
import io.github.msumsh.util.JsonUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {

    protected JsonUtil jsonUtil;
    protected IpValidator ipValidator;
    protected DomainService service;
    protected Path tempFile;

    @BeforeMethod
    public void setUp() throws IOException {

        tempFile = Files.createTempFile("domains", ".json");

        jsonUtil = new JsonUtil();
        ipValidator = new IpValidator();

        service = new DomainService(
                jsonUtil,
                tempFile,
                ipValidator
        );
    }

    @AfterMethod
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}
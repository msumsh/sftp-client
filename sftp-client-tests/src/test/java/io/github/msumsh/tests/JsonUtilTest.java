package io.github.msumsh.tests;

import io.github.msumsh.model.DomainAddress;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonUtilTest extends BaseTest {
    @Test
    public void readShouldReturnAddressesFromValidJson() throws IOException {
        String json =
                "{\n" +
                        "  \"addresses\": [\n" +
                        "    {\n" +
                        "      \"domain\": \"test.com\",\n" +
                        "      \"ip\": \"1.1.1.1\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"domain\": \"localhost\",\n" +
                        "      \"ip\": \"127.0.0.1\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

        Files.write(tempFile, json.getBytes(StandardCharsets.UTF_8));

        List<DomainAddress> result = jsonUtil.read(tempFile);

        Assert.assertEquals(result.size(), 2);

        Assert.assertEquals(result.get(0).getDomain(), "test.com");
        Assert.assertEquals(result.get(0).getIp(), "1.1.1.1");

        Assert.assertEquals(result.get(1).getDomain(), "localhost");
        Assert.assertEquals(result.get(1).getIp(), "127.0.0.1");
    }

    @Test
    public void readShouldReturnEmptyListForEmptyFile() throws IOException {
        Files.write(tempFile, new byte[0]);

        List<DomainAddress> result = jsonUtil.read(tempFile);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void readShouldThrowExceptionForUnsupportedJsonFormat() throws IOException {
        String json =
                "{\"addresses\":[{\"domain\":\"test.com\",\"ip\":\"1.1.1.1\"}]}";

        Files.write(tempFile, json.getBytes(StandardCharsets.UTF_8));

        Assert.expectThrows(IOException.class,
                () -> jsonUtil.read(tempFile));
    }

    @Test
    public void writeShouldCreateCorrectJsonFile() throws IOException {
        List<DomainAddress> addresses = Arrays.asList(
                new DomainAddress("test.com", "1.1.1.1"),
                new DomainAddress("localhost", "127.0.0.1")
        );

        jsonUtil.write(tempFile, addresses);

        List<DomainAddress> result = jsonUtil.read(tempFile);

        Assert.assertEquals(result.size(), 2);

        Assert.assertEquals(result.get(0).getDomain(), "test.com");
        Assert.assertEquals(result.get(1).getDomain(), "localhost");
    }

    @Test
    public void writeShouldWriteEmptyAddressesArray() throws IOException {
        jsonUtil.write(tempFile, Collections.emptyList());

        String json = new String(
                Files.readAllBytes(tempFile),
                StandardCharsets.UTF_8
        );

        Assert.assertTrue(json.contains("\"addresses\""));
        Assert.assertTrue(json.contains("["));
        Assert.assertTrue(json.contains("]"));
    }

    @Test
    public void readShouldReturnEmptyListWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(tempFile);

        List<DomainAddress> result = jsonUtil.read(tempFile);

        Assert.assertTrue(result.isEmpty());
    }
}
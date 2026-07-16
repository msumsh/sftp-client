package io.github.msumsh;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import io.github.msumsh.cli.ConsoleMenu;
import io.github.msumsh.client.SftpClient;
import io.github.msumsh.service.DomainService;
import io.github.msumsh.util.IpValidator;
import io.github.msumsh.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path jsonPath = Paths.get("domains.json");

        JsonUtil jsonUtil = new JsonUtil();

        DomainService service = new DomainService(jsonUtil, jsonPath, new IpValidator());

        Scanner scanner = new Scanner(System.in);

        ConsoleMenu menu = new ConsoleMenu(
                true,
                scanner,
                service
        );

        Properties cfg = new Properties();


        try (InputStream input = Files.newInputStream(Paths.get("config.properties"))) {
            cfg.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }

        String host = cfg.getProperty("host", "localhost");

        int port;

        try {
            port = Integer.parseInt(cfg.getProperty("port", "22"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid port in config", e);
        }

        String login = cfg.getProperty("login", "user");
        String password = cfg.getProperty("password", "123");

        String remoteFile = cfg.getProperty("remoteFile", "/home/test/domains.json");

        SftpClient client = new SftpClient(host, port, login, password);

        try {
            client.connect();

            client.download(remoteFile, jsonPath);

            service.load();

            menu.work();

            client.upload(jsonPath, remoteFile);
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (SftpException e) {
            System.err.println("SFTP error: " + e.getMessage());
       } catch (JSchException e) {
            System.err.println("JSch error: " + e.getMessage());
       } finally {
            client.disconnect();

            scanner.close();
        }
    }
}
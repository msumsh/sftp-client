package io.github.msumsh.util;

import io.github.msumsh.model.DomainAddress;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public List<DomainAddress> read(Path path) {
        List<DomainAddress> addresses = new ArrayList<>();

        if (!Files.exists(path)) {
            System.out.println("File doesn't exist");
            return addresses;
        }

        List<String> data;
        try {
            data = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
            return addresses;
        }

        if (data.isEmpty()) {
            System.out.println("Empty data");
            return addresses;
        }

        String domain = "";
        String ip = "";
        for (String line : data) {
            if (line.contains("}")) {
                addresses.add(new DomainAddress(domain, ip));
            } else if (line.contains("domain")) {
                domain = parseJsonObject(line);
            } else if (line.contains("ip")) {
                ip = parseJsonObject(line);
            }
        }

        return addresses;
    }

    /*
    ===== IN PROGRESS =====

    public void write(Path path, List<DomainAddress> domains) {
        if (!Files.exists(path)) {
            System.out.println("File doesn't exist");
        }

        StringBuilder builder = new StringBuilder();

        String indent = "    ";

        builder.append("{\n");

        builder.append(indent);
        indent += indent;

        builder.append("\"addresses\": [");

        for (DomainAddress addr : domains) {
            builder.append(indent);
            indent += indent;

            builder.append("{\n");

            builder.append(indent);

            builder.append("\"domain\": ");
            builder.append(addr.getDomain());
            builder.append(",\n");

            builder.append("\"ip\": ");
            builder.append(addr.getIp());
            builder.append("\n");

            indent = indent.substring(0, 4);
            builder.append(indent);

            builder.append("},\n");
        }



    }
     */

    private String parseJsonObject(String line) {
        return line.substring(line.indexOf(":") + 1, line.indexOf(","))
                .replace("\"", "")
                .trim();
    }
}

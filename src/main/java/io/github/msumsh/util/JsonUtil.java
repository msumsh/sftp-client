package io.github.msumsh.util;

import io.github.msumsh.model.DomainAddress;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public List<DomainAddress> read(Path path) throws IOException {
        List<DomainAddress> addresses = new ArrayList<>();

        if (!Files.exists(path)) {
            return addresses;
        }

        List<String> data = Files.readAllLines(path);

        if (data.isEmpty()) {
            return addresses;
        }

        String domain = "";
        String ip = "";
        for (String line : data) {
            line = line.trim();

            if (line.equals("}") || line.equals("},")) {
                if (!domain.isEmpty() && !ip.isEmpty()) {
                    addresses.add(new DomainAddress(domain, ip));
                }

                domain = "";
                ip = "";
            } else if (line.startsWith("\"domain\"")) {
                domain = parseJsonObject(line);
            } else if (line.startsWith("\"ip\"")) {
                ip = parseJsonObject(line);
            }
        }

        return addresses;
    }

    public void write(Path path, List<DomainAddress> domains) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        StringBuilder builder = new StringBuilder();

        builder.append("{\n");

        builder.append("    \"addresses\": [\n");

        for (int i = 0; i < domains.size(); i++) {
            writeDomain(builder, domains.get(i), i == domains.size() - 1);
        }

        builder.append("    ]\n");

        builder.append("}\n");

        try (BufferedWriter writer = Files.newBufferedWriter(path) ){
            writer.write(builder.toString());
        }
    }

    private String parseJsonObject(String line) throws IllegalArgumentException {
        if (!line.contains(":")) {
            throw new IllegalArgumentException("Invalid JSON line: " + line);
        }

        int begin = line.indexOf(":") + 1;

        int end = line.contains(",")
                ? line.indexOf(",")
                : line.length();

        return line.substring(begin, end)
                .replace("\"", "")
                .trim();
    }

    private void writeDomain(StringBuilder builder, DomainAddress addr, boolean isLast) {
        builder.append("        {\n");

        builder.append("            \"domain\": \"");
        builder.append(addr.getDomain());
        builder.append("\",\n");

        builder.append("            \"ip\": \"");
        builder.append(addr.getIp());
        builder.append("\"\n");

        builder.append("        }");

        if (!isLast) {
            builder.append(",");
        }

        builder.append("\n");
    }
}

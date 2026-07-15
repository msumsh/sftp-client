package io.github.msumsh.service;

import io.github.msumsh.model.DomainAddress;
import io.github.msumsh.util.IpValidator;
import io.github.msumsh.util.JsonUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DomainService {
    private final JsonUtil jsonUtil;
    private final Path jsonPath;
    private final IpValidator ipValidator;

    private List<DomainAddress> domains;

    public DomainService(JsonUtil jsonUtil, Path jsonPath, IpValidator ipValidator) {
        this.jsonUtil = jsonUtil;
        this.jsonPath = jsonPath;
        this.ipValidator = ipValidator;
        this.domains = new ArrayList<>();
    }

    public void load() {
        domains = jsonUtil.read(jsonPath);

        sort();
    }

    public void save() {
        jsonUtil.write(jsonPath, domains);
    }

    public List<DomainAddress> getAll() {
        return new ArrayList<>(domains);
    }

    public DomainAddress findByDomain(String domain) {
        for (DomainAddress entry : domains) {
            if (domain.equalsIgnoreCase(entry.getDomain())) {
                return entry;
            }
        }

        return null;
    }

    public DomainAddress findByIp(String ip) {
        for (DomainAddress entry : domains) {
            if (ip.equals(entry.getIp())) {
                return entry;
            }
        }

        return null;
    }

    public void add(String domain, String ip) {
        DomainAddress addrByIp;

        boolean isValidIp = ipValidator.validate(ip);
        if (isValidIp) {
            addrByIp = findByIp(ip);
        } else {
            throw new IllegalArgumentException("Invalid IPv4 address");
        }

        DomainAddress addrByDomain = findByDomain(domain);

        if (addrByIp != null) {
            throw new IllegalArgumentException("Address with this IP-address already exists");
        }

        if (addrByDomain != null) {
            throw new IllegalArgumentException("Address with this domain already exists");
        }

        DomainAddress newDomainAddress = new DomainAddress(domain, ip);

        domains.add(newDomainAddress);

        sort();

        save();
    }

    public void deleteByDomain(String domain) {
        DomainAddress addr = findByDomain(domain);

        if (addr != null) {
            delete(addr);
        }
    }

    public void deleteByIp(String ip) {
        DomainAddress addr = findByIp(ip);

        if (addr != null) {
            delete(addr);
        }
    }

    private void sort() {
        domains.sort(Comparator.comparing(DomainAddress::getDomain));
    }

    private void delete(DomainAddress adr) {
        domains.remove(adr);

        save();
    }
}

package io.github.msumsh.model;

public class DomainAddress {
    private String domain;
    private String ip;

    public DomainAddress() {
        this.domain = "";
        this.ip = "";
    }

    public DomainAddress(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public String getIp() {
        return ip;
    }
}
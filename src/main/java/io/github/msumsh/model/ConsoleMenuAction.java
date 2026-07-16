package io.github.msumsh.model;

public enum ConsoleMenuAction {
    GET_PAIRS("Get \"domain - address\" pairs from the file"),
    GET_IP("Get an IP-address by domain name"),
    GET_DOMAIN("Get a domain name by IP-address"),
    ADD_PAIR("Add a new \"domain - address\" pair to the file"),
    DELETE_PAIR_BY_DOMAIN("Delete \"domain - address\" pairs from the file by domain"),
    DELETE_PAIR_BY_IP("Delete \"domain - address\" pairs from the file by IP-address"),
    TERMINATE("Terminate");

    private final String displayName;

    ConsoleMenuAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

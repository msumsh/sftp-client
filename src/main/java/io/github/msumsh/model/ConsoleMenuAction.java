package io.github.msumsh.model;

public enum ConsoleMenuAction {
    GET_PAIRS("Get \"domain - address\" pairs from the file"),
    GET_IP("Get an IP-address by domain name"),
    GET_DOMAIN("Get a domain name by IP-address"),
    ADD_PAIR("Add a new \"domain - address\" pair to the file"),
    DELETE_PAIR("Delete \"domain - address\" pairs from the file"),
    TERMINATE("Terminate");

    private final String displayName;

    ConsoleMenuAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

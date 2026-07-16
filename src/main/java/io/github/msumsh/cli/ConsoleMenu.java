package io.github.msumsh.cli;

import io.github.msumsh.model.ConsoleMenuAction;
import io.github.msumsh.model.DomainAddress;
import io.github.msumsh.service.DomainService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private boolean state;
    private final Scanner scanner;

    private final DomainService service;

    public ConsoleMenu(boolean state, Scanner scanner, DomainService service) {
        this.state = state;
        this.scanner = scanner;
        this.service = service;
    }

    public void work() throws IOException {
        System.out.println("===== CONSOLE SFTP-CLIENT APPLICATION =====");

        ConsoleMenuAction[] actions = ConsoleMenuAction.values();

        while (state) {
            displayActions("===== LIST OF AVAILABLE ACTIONS =====", actions);

            ConsoleMenuAction selectedOption = select("Select an action from the list by choosing a number.",
                    "Please enter the valid number. Attempts left: ",
                    "Invalid action. Termination", actions);

            execute(selectedOption);
        }
    }

    private void displayActions(String msg, ConsoleMenuAction[] actions) {
        System.out.println(msg);

        for (int i = 1; i <= actions.length; i++) {
            System.out.println(i + ". " + (actions[i - 1]).getDisplayName());
        }
    }

    private ConsoleMenuAction select(String msg, String invalidInputMsg, String errMsg, ConsoleMenuAction[] actions) {
        System.out.println(msg);

        int selectedOption = chooseNumber(invalidInputMsg, errMsg, 1, actions.length);

        return actions[selectedOption - 1];
    }

    private int chooseNumber(String msg, String errMsg, int minNum, int maxNum) {
        int attempts = 3;

        System.out.println(msg + " " + attempts);

        while (attempts > 0) {
            if (!scanner.hasNextInt()) {
                attempts--;
                scanner.next();
                System.out.println(msg + " " + attempts);
                continue;
            }

            int selectedOption = scanner.nextInt();
            scanner.nextLine();

            if (selectedOption  < minNum || selectedOption > maxNum) {
                attempts--;
                System.out.println(msg + " " + attempts);
            } else {
                return selectedOption;
            }
        }

        System.out.println(errMsg);

        state = false;

        return maxNum;
    }

    private void execute(ConsoleMenuAction action) throws IOException {
        switch(action) {
            case GET_PAIRS:
                showAddresses();
                break;
            case GET_IP:
                showByDomain();
                break;
            case GET_DOMAIN:
                showByIp();
                break;
            case ADD_PAIR:
                addPair();
                break;
            case DELETE_PAIR:
                deletePair();
                break;
            case TERMINATE:
                state = false;
                break;
        }
    }

    private void showAddresses() {
        List<DomainAddress> addresses = service.getAll();

        for (DomainAddress addr : addresses) {
            System.out.println(addr);
        }
    }

    private void showByDomain() {
        System.out.println("Enter the domain:");
        String domain = scanner.nextLine();

        DomainAddress addr = service.findByDomain(domain);

        if (addr != null) {
            System.out.println(addr.getIp());
        } else {
            System.out.println("Address with this domain doesn't exist");
        }
    }

    private void showByIp() {
        System.out.println("Enter the IP-address:");
        String ip = scanner.nextLine();

        DomainAddress addr = service.findByIp(ip);

        if (addr != null) {
            System.out.println(addr.getDomain());
        } else {
            System.out.println("Address with this IP-address doesn't exist");
        }
    }

    private void addPair() throws IOException {
        System.out.println("Enter the domain:");
        String domain = scanner.nextLine();

        System.out.println("Enter the IP-address:");
        String ip = scanner.nextLine();

        service.add(domain, ip);
    }

    private void deletePair() throws IOException {
        System.out.println("Enter the domain:");
        String domain = scanner.nextLine();

        System.out.println("Enter the IP-address:");
        String ip = scanner.nextLine();

        service.delete(domain, ip);
    }
}
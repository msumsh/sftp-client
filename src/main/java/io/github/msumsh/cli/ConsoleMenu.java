package io.github.msumsh.cli;

import io.github.msumsh.model.ConsoleMenuAction;

import java.util.Scanner;

public class ConsoleMenu {
    private boolean state;
    private final Scanner scanner;

    public ConsoleMenu() {
        this.state = false;
        this.scanner = new Scanner(System.in);
    }

    public ConsoleMenu(boolean state) {
        this.state = state;
        this.scanner = new Scanner(System.in);
    }

    public ConsoleMenu(boolean state, Scanner scanner) {
        this.state = state;
        this.scanner = scanner;
    }

    public void showMenu() {
        System.out.println("===== CONSOLE SFTP-CLIENT APPLICATION =====");

        ConsoleMenuAction[] actions = ConsoleMenuAction.values();

        while (state) {
            displayActions("===== LIST OF AVAILABLE ACTIONS =====", actions);

            ConsoleMenuAction selectedOption = select("Select an action from the list by choosing a number: ",
                    "Please enter the valid number. Attempts left: ",
                    "Invalid action. Termination", actions);


        }
    }

    private void displayActions(String msg, ConsoleMenuAction[] actions) {
        System.out.println(msg);

        for (int i = 1; i <= actions.length; i++) {
            System.out.println(i + ". " + actions[i]);
        }
    }

    private ConsoleMenuAction select(String msg, String invalidInputMsg, String errMsg, ConsoleMenuAction[] actions) {
        System.out.println(msg);

        int selectedOption = chooseNumber(invalidInputMsg, errMsg, 1, actions.length + 1);

        return actions[selectedOption];
    }

    private int chooseNumber(String msg, String errMsg, int minNum, int maxNum) {
        System.out.println(msg);

        int attempts = 3;
        while (attempts > 0) {
            if (!scanner.hasNextLong()) {
                attempts--;
                scanner.next();
                System.out.println(msg + attempts);
                continue;
            }

            int selectedOption = scanner.nextInt();
            scanner.nextLine();

            if (selectedOption  < minNum || selectedOption > maxNum) {
                attempts--;
                System.out.println(msg + " Attempts left: " + attempts);
            } else {
                return selectedOption;
            }
        }

        System.out.println(errMsg);

        return maxNum;
    }
}
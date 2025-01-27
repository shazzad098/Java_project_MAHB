package com.shazzad.librarysystem.util;

import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static int getIntInput(String prompt) {
        int value = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.print(prompt + ": ");
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline left by nextInt()
                isValidInput = true;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        return value;
    }

    // You can add more methods for other input types (e.g., getDoubleInput, getBooleanInput) if needed later.
}
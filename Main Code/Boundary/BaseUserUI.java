package Boundary;

import java.util.Scanner;

import Entity.User;

public abstract class BaseUserUI {
    protected User user;
    protected Scanner scanner;

    public BaseUserUI(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in); // Can be passed in instead if needed
    }

    protected void changePassword() {
        System.out.print("Enter current password: ");
        String current = scanner.nextLine();
        if (!current.equals(user.getPassword())) {
            System.out.println("Incorrect current password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine();
        System.out.print("Confirm new password: ");
        String confirm = scanner.nextLine();

        if (!newPass.equals(confirm)) {
            System.out.println("Passwords do not match.");
            return;
        }

        user.changePassword(newPass);
    }

    // Make child classes implement this
    public abstract boolean run();
}

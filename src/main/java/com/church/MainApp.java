// Relis Joseph
// Church Member Management System
// CEN Software Development Project
// Professor Lissa
// March 2026

// Description:
// This software allows users to manage members of a church congregation.
// The program provides functionality to add members manually or import them
// from a CSV file. It also allows users to view, update, and delete member
// records, as well as track tithing information.
//
// Future updates may include additional features to improve usability
// and expand the system’s capabilities.
//
// For questions or support, contact: lihoj754@gmail.com

package com.church;
import com.church.dao.MemberDAO;
import com.church.model.Member;
import com.church.service.MemberService;
import com.church.util.DatabaseInitializer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        DatabaseInitializer.initialization();

        MemberDAO dao = new MemberDAO();
        MemberService service = new MemberService(dao);
        Scanner scanner = new Scanner(System.in);

        //List of choice
        System.out.println("\nWelcome to the Church of God of Prophecy App"
                + "\nPress 1 to add a member manually"
                + "\nPress 2 to add load from path"
                + "\nPress 3 to view the list of members"
                + "\nPress 4 to update a member"
                + "\nPress 5 to delete a member"
                + "\nPress 6 to get the total of tithing"
                + "\nAt anytime type \"QUIT\" to quit or \"MAIN\" to go to the main");

        while (true) {
            System.out.print("Choose an option: ");

            String quitandback = (scanner.nextLine().trim());

            if (quitandback.equalsIgnoreCase("quit")){
                return;
            }
            if (quitandback.equalsIgnoreCase("MAIN")) {
                System.out.println("Returning to main menu...");
                continue; // goes back to menu without parsing as integer
            }

            int choice;

            try {
                try {
                    choice = Integer.parseInt(quitandback);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number from the menu.");
                    continue;
                }

                String married;
                String baptize;
                List<Member> members = dao.getAllMembers();

                switch (choice) {
                    case 1:
                        service.addMemberManually(scanner);
                        service.saveMembersToCSV("C:\\Users\\relis\\Desktop\\members.csv");
                        break;

                    case 2:
                        System.out.print("Please enter the path here: ");
                        String filename = scanner.nextLine().trim();
                        service.addMembersFromFile(filename);
                        break;

                    case 3:
                        //Display members stored in the database
                        if (members.isEmpty()) {
                            System.out.println("Sorry there is no member available to display");
                            break;
                        }
                        for (Member mem : members) {
                            married = mem.is_married() ? "Married" : "Not Married";
                            baptize = mem.is_baptized() ? "Baptized" : "Not Baptized";
                            System.out.println(STR."\{mem.get_member_id()} - \{mem.get_first_name()} - \{mem.get_last_name()} - \{mem.get_phone_number()} - \{mem.get_email()} - \{baptize} - \{married} - \{mem.get_membership_status()} - \{mem.get_tithing_amount()}");
                        }
                        break;

                    case 4:
                        if (members.isEmpty()) {
                            System.out.println("Sorry there is no member available to update");
                            break;
                        }
                        //Option to update a member by ID
                        int uid = getValidIntInput(scanner, "Enter the member ID to update: ", 1, Integer.MAX_VALUE);
                        Member update = new Member();
                        update.set_member_id(uid);

                        String phone = getValidPhone(scanner);
                        update.set_phone_number(phone);

                        String email = getValidEmail(scanner, "New Email: ");
                        update.set_email(email);

                        String status = getValidStatus(scanner, "New Status (Active/Inactive): ");
                        update.set_membership_status(status);

                        double tithing = getValidDouble(scanner, "New Tithing: ", 0, Double.MAX_VALUE);
                        update.set_tithing_amount(tithing);

                        boolean baptized = getValidBoolean(scanner, "Baptized (Y/N): ");
                        update.set_baptized(baptized);

                        boolean marriedStatus = getValidBoolean(scanner, "Married (Y/N): ");
                        update.set_married(marriedStatus);

                        dao.updateMember(update);
                        System.out.println("Member updated successfully.");
                        service.saveMembersToCSV("C:\\Users\\relis\\Desktop\\members.csv");
                        break;

                    case 5:
                        //Option to delete a member by ID
                        if (members.isEmpty()) {
                            System.out.println("Sorry there is no member available to delete");
                            break;
                        }
                        int did = getValidIntInput(scanner, "Member ID to delete: ", 1, Integer.MAX_VALUE);
                        dao.deleteMember(did);
                        System.out.println("Deleted successfully.");
                        service.saveMembersToCSV("C:\\Users\\relis\\Desktop\\members.csv");
                        break;

                    case 6:
                        //Option to display contributions
                        if (members.isEmpty()) {
                            System.out.println("Sorry there is no contributions available to display");
                            break;
                        }
                        double total = dao.getTotalTithing();
                        System.out.println("Total of Tithing Received: $" + total);
                        break;
                    default:
                        System.out.println("Please enter a number between 1 and 7.");
                }

            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Helper methods for input validation
    private static int getValidIntInput(Scanner scanner, String prompt, int min, int max) {
        int value = -1;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric value.");
            }
        }
        return value;
    }

    //Method to validate tithe
    private static double getValidDouble(Scanner scanner, String prompt, double min, double max) {
        double value = -1;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = Double.parseDouble(input);
                if (value < min || value > max) {
                    System.out.println("Value must be between " + min + " and " + max);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric value.");
            }
        }
        return value;
    }

    //Method to validate email form
    private static String getValidEmail(Scanner scanner, String prompt) {
        String email = "";
        while (true) {
            System.out.print(prompt);
            email = scanner.nextLine().trim();
            if (email.contains("@") && !email.contains(" ")) break;
            System.out.println("Error: Please enter a valid email address.");
        }
        return email;
    }

    //Method to validate phone number
    private static String getValidPhone(Scanner scanner) {
        String phone = "";
        while (true) {
            System.out.print("New Phone: ");
            phone = scanner.nextLine().trim();
            if (phone.matches("[0-9+\\-() ]+")) break;
            System.out.println("Please enter a valid phone number.");
        }
        return phone;
    }

    //Method to validate member's status
    private static String getValidStatus(Scanner scanner, String prompt) {
        String status = "";
        while (true) {
            System.out.print(prompt);
            status = scanner.nextLine().trim();
            if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive")) break;
            System.out.println("Status must be 'Active' or 'Inactive'.");
        }
        return status;
    }

    //Method to validate married and baptized status
    private static boolean getValidBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.startsWith("Y")) return true;
            if (input.startsWith("N")) return false;
            System.out.println("Invalid input. Enter Y or N.");
        }
    }
}
//C:\Users\relis\Desktop\members.csv
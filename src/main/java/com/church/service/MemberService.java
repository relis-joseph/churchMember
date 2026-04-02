package com.church.service;
import com.church.dao.MemberDAO;
import com.church.model.Member;

import java.io.*;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class MemberService {

    private final MemberDAO dao;

    public MemberService(MemberDAO dao) {
        this.dao = dao;
    }
    // GUI version: add member using parameters
    public Member addMemberManually(String firstName, String lastName, String dob,
                                    String phone, String email, String status,
                                    double tithing, boolean baptized, boolean married) {

        Member member = new Member(firstName, lastName, dob, phone, email, status, tithing, baptized, married);

        try {
            dao.addMember(member);
            return member;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Add a single member manually
    public boolean addMemberManually(Scanner scanner) {
        String first, last, dob = "", phone, email, status, bapt, marry;
        double tithing;
        boolean baptized = false, married = false;

        //Adding member firstname
        try {
            do {
                System.out.print("First Name: ");
                first = scanner.nextLine();

                if (first.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false; // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(first);
                //Validate name
                if (!first.matches("[a-zA-Z ]+") || first.length() < 2){
                    System.out.println("Error: Please enter a valid firstname");
                    first = "";
                }
            }while (first.isEmpty() || !first.matches("[a-zA-Z ]+"));

            //Adding member lastname
            do {
                System.out.print("Last Name: ");
                last = scanner.nextLine();

                if (last.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(last);

                //Validate lastname
                if (!last.matches("[a-zA-Z ]+") || last.length() < 2){
                    System.out.println("Error: Please enter a valid lastname");
                    last = "";
                }
            }while (last.isEmpty() || !last.matches("[a-zA-Z ]+"));

            //Adding member date of birth
            do {
                System.out.print("DOB: (MM/DD/YYYY) ");
                dob = scanner.nextLine().trim();

                if (dob.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(dob);

                //Validate date of birth
                if (!dob.matches("\\d{2}/\\d{2}/\\d{4}")){
                    System.out.println("Error: Please enter a valid DOB in format MM/DD/YYYY");
                    dob = "";
                }
            } while (!(dob.contains("/")));

            //Adding member phone number
            do {
                System.out.print("Phone: ");
                phone = scanner.nextLine().trim();

                if (phone.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(phone);

                //Validate phone number
                if (!phone.matches("[0-9+\\-() ]+")) {
                    System.out.println("Error: Please enter a valid phone number.");
                    phone = ""; // force loop to repeat
                }
            } while (phone.isEmpty());


            //Adding member email address
            do {
                System.out.print("Email address: ");
                email = scanner.nextLine();

                if (email.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(email);

                //Validate email address
                if (!last.matches("[a-zA-Z ]+") || !email.contains("@") || email.contains(" ")){
                    System.out.println("Error: Please enter a valid email address");
                    email = "";
                }
            }while (!last.matches("[a-zA-Z ]+") || !email.contains("@") || email.contains(" "));


            //Adding status of member
            do {
                System.out.print("Status (Active/Inactive): ");
                status = scanner.nextLine().trim();

                if (status.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(status);

                //Validate status entry
                if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("A")) {
                    status = "Active"; // normalize
                } else if (status.equalsIgnoreCase("Inactive") || status.equalsIgnoreCase("I")) {
                    status = "Inactive"; // normalize
                } else {
                    System.out.println("Error: Status must be Active or Inactive (or A/I).");
                    status = "";
                }
            } while (status.isEmpty());


            //Adding contributions
            do {
                System.out.print("Tithing: ");

                //Validate tithe entry
                while (!scanner.hasNextDouble()) {
                    System.out.println("Error: Please enter a valid numeric amount.");
                    scanner.next(); // discard invalid input
                    System.out.print("Tithing: ");
                }

                tithing = scanner.nextDouble();

                if (String.valueOf(tithing).equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(String.valueOf(tithing));

                scanner.nextLine(); // clear leftover newline
            } while (tithing < 0);


            //Member baptized
            do {
                System.out.print("Baptized (Y/N): ");
                bapt = scanner.nextLine().trim().toUpperCase();

                if (bapt.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(bapt);

                if (bapt.startsWith("Y")) {
                    baptized = true;
                } else if (bapt.startsWith("N")) {
                    baptized = false;
                } else {
                    System.out.println("Error: Invalid input. Enter Y or N.");
                    bapt = "";
                }
            } while (bapt.isEmpty());

            //Adding member married status
            do {
                System.out.print("Married (Y/N): ");
                marry = scanner.nextLine().trim().toUpperCase();

                if (marry.equalsIgnoreCase("MAIN")) {
                    System.out.println("Returning to main menu...");
                    return false;
                    // goes back to the menu loop
                }

                //Calling quit or main
                quitandback(marry);

                if (marry.startsWith("Y")) {
                    married = true;
                } else if (marry.startsWith("N")) {
                    married = false;
                } else {
                    System.out.println("Error: Invalid input. Enter Y or N.");
                    marry = "";
                }
            } while (marry.isEmpty());

            //Add member information to the Member class
            Member m = new Member(first, last, dob, phone, email, status, tithing, baptized, married);
            dao.addMember(m);

            System.out.println("Member added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baptized;
    }

    // Add members from a CSV file
    public boolean addMembersFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File not found or invalid path: " + filename);
            return false; // indicate failure
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { // skip header
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 9) {
                    System.out.println(MessageFormat.format("Skipping invalid line: {0}", line));
                    continue;
                }

                Member m = getMember(parts);
                dao.addMember(m);
            }
            System.out.println("Members added from file successfully.");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //Method to get member from Member class
    private static Member getMember(String[] parts) {
        String first = parts[0].trim();
        String last = parts[1].trim();
        String dob = parts[2].trim();
        String phone = parts[3].trim();
        String email = parts[4].trim();
        String status = parts[5].trim();
        double tithing = Double.parseDouble(parts[6].trim());
        boolean baptized = Boolean.parseBoolean(parts[7].trim());
        boolean married = Boolean.parseBoolean(parts[8].trim());

        Member m = new Member(first, last, dob, phone, email, status, tithing, baptized, married);
        return m;
    }

    //Method to save member to csv file
    public boolean saveMembersToCSV(String filename) {

        try {
            List<Member> members = dao.getAllMembers();

            try (PrintWriter pw = new PrintWriter(filename)) {

                pw.println("FirstName,LastName,DOB,Phone,Email,Status,Tithing,Baptized,Married");

                for (Member m : members) {
                    pw.printf("%s,%s,%s,%s,%s,%s,%.2f,%b,%b%n",
                            m.get_first_name(),
                            m.get_last_name(),
                            m.get_date_of_birth(),
                            m.get_phone_number(),
                            m.get_email(),
                            m.get_membership_status(),
                            m.get_tithing_amount(),
                            m.is_baptized(),
                            m.is_married());
                }
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    //Method to quit and go back
    public boolean quitandback(String quitandback) {
        if (quitandback.toLowerCase().matches("quit")) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        return false;
    }
}
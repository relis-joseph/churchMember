package com.church.model;

public class Member {

    private int member_id;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private String phone_number;
    private String email;
    private String membership_status;
    private double tithing_amount;
    private boolean is_baptized;
    private boolean is_married;

    public Member() {}

    public Member(String first_name, String last_name, String date_of_birth,
                  String phone_number, String email,
                  String membership_status,
                  double tithing_amount, boolean is_baptized, boolean is_married) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.phone_number = phone_number;
        this.email = email;
        this.membership_status = membership_status;
        this.tithing_amount = tithing_amount;
        this.is_baptized = is_baptized;
        this.is_married = is_married;
    }

    // Getters & Setters

    public int get_member_id() { return member_id; }
    public void set_member_id(int member_id) { this.member_id = member_id; }

    public String get_first_name() { return first_name; }
    public void set_first_name(String first_name) { this.first_name = first_name; }

    public String get_last_name() { return last_name; }
    public void set_last_name(String last_name) { this.last_name = last_name; }

    public String get_date_of_birth() { return date_of_birth; }
    public void set_date_of_birth(String date_of_birth) { this.date_of_birth = date_of_birth; }

    public String get_phone_number() { return phone_number; }
    public void set_phone_number(String phone_number) { this.phone_number = phone_number; }

    public String get_email() { return email; }
    public void set_email(String email) { this.email = email; }

    public String get_membership_status() { return membership_status; }
    public void set_membership_status(String membership_status) { this.membership_status = membership_status; }

    public double get_tithing_amount() { return tithing_amount; }
    public void set_tithing_amount(double tithing_amount) { this.tithing_amount = tithing_amount; }

    public boolean is_baptized() { return is_baptized; }
    public void set_baptized(boolean baptized) { this.is_baptized = baptized; }

    public boolean is_married() { return is_married; }
    public void set_married(boolean married) { this.is_married = married; }
}

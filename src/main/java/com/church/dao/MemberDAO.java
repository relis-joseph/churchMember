package com.church.dao;

import com.church.model.Member;
import com.church.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // method to create member
    public boolean addMember(Member member) throws SQLException {

        String sql = "INSERT INTO members(first_name,last_name,date_of_birth,phone_number,email_address,membership_status,tithing_amount,is_baptized,is_married) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.get_first_name());
            pstmt.setString(2, member.get_last_name());
            pstmt.setString(3, member.get_date_of_birth());
            pstmt.setString(4, member.get_phone_number());
            pstmt.setString(5, member.get_email());
            pstmt.setString(6, member.get_membership_status());
            pstmt.setDouble(7, member.get_tithing_amount());
            pstmt.setInt(8, member.is_baptized() ? 1 : 0);
            pstmt.setInt(9, member.is_married() ? 1 : 0);

            return pstmt.executeUpdate() > 0;
        }
    }

    // method to read member
    public List<Member> getAllMembers() throws SQLException {

        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Member member = new Member();
                member.set_member_id(rs.getInt("member_id"));
                member.set_first_name(rs.getString("first_name"));
                member.set_last_name(rs.getString("last_name"));
                member.set_date_of_birth(rs.getString("date_of_birth"));
                member.set_phone_number(rs.getString("phone_number"));
                member.set_email(rs.getString("email_address"));
                member.set_membership_status(rs.getString("membership_status"));
                member.set_tithing_amount(rs.getDouble("tithing_amount"));
                member.set_baptized(rs.getInt("is_baptized") == 1);
                member.set_married(rs.getInt("is_married") == 1);

                members.add(member);
            }
        }
        return members;
    }

    // method to update member by ID
    public boolean updateMember(Member member) throws SQLException {

        String sql = "UPDATE members SET phone_number=?, email_address=?, membership_status=?, tithing_amount=?, is_baptized=?, is_married=? WHERE member_id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.get_phone_number());
            pstmt.setString(2, member.get_email());
            pstmt.setString(3, member.get_membership_status());
            pstmt.setDouble(4, member.get_tithing_amount());
            pstmt.setInt(5, member.is_baptized() ? 1 : 0);
            pstmt.setInt(6, member.is_married() ? 1 : 0);
            pstmt.setInt(7, member.get_member_id());

            return pstmt.executeUpdate() > 0;
        }
    }
    // method to delete member by ID
    public boolean deleteMember(int id) throws SQLException {

        String sql = "DELETE FROM members WHERE member_id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
    // custom action
    public double getTotalTithing() throws SQLException {

        String sql = "SELECT SUM(tithing_amount) FROM members";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getDouble(1);
        }
    }
}

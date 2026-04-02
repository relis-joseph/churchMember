package com.church.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialization() {
        //Automatically drop table when app open
        String drop = "DROP TABLE IF EXISTS members;";
        //Create new table
        String create = """
CREATE TABLE IF NOT EXISTS members (
    member_id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    date_of_birth TEXT,
    phone_number TEXT,
    email_address TEXT,
    membership_status TEXT CHECK(membership_status IN ('Active','Inactive')),
    join_date TEXT DEFAULT CURRENT_TIMESTAMP,
    tithing_amount REAL DEFAULT 0.0,
    is_baptized INTEGER,
    is_married INTEGER
);
""";


        //Connect Database
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (Statement stmt = connection.createStatement()) {

                stmt.execute(drop);
                stmt.execute(create);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

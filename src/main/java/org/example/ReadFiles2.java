package org.example;

import java.io.*;
import java.sql.*;
import java.util.concurrent.CompletableFuture;
import com.opencsv.CSVReader;

public class ReadFiles2 {

    private static final String db_url  = "jdbc:postgresql://localhost:5432/ReadFiles";
    private static final String username    = "postgres";
    private static final String password    = "AhmadSajidSura2003";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(db_url, username, password);
            System.out.println("Connected to DB");

            CompletableFuture<Void> txtTask = CompletableFuture.runAsync(() -> {
                insertFromTxt("src/main/java/org/example/file1.txt", connection);
            });

            CompletableFuture<Void> csvTask = CompletableFuture.runAsync(() -> {
                insertFromCsv("src/main/java/org/example/file1.csv", connection);
            });
            CompletableFuture.allOf(txtTask, csvTask).join();

            System.out.println("Done!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertFromTxt(String filename, Connection connection) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                insertRecord(data[0], data[1], connection);
            }

            System.out.println("TXT records inserted");

        } catch (IOException e) {
            System.out.println("Error in TXT file");
        }
    }

    private static void insertFromCsv(String filename, Connection connection) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(filename));
            String[] nextLine;
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                insertRecord(nextLine[0], nextLine[1], connection);
            }

            System.out.println("CSV records inserted");

        } catch (Exception e) {
            System.out.println("Error in CSV file");
        }
    }

    private static void insertRecord(String name, String email, Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO students(name, email) VALUES ('" + name + "', '" + email + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Insert error");
        }
    }
}


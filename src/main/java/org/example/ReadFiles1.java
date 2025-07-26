package org.example;

import java.io.*;
import java.sql.*;

public class ReadFiles1 {

    private static final String DB_URL  = "jdbc:postgresql://localhost:5432/ReadFiles";
    private static final String USER    = "postgres";
    private static final String PASS    = "AhmadSajidSura2003";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to SQL");

            insertFromTxt("src/main/java/org/example/file1.txt", conn);
            System.out.println("Added successfully from txt file");

            insertFromCsv("src/main/java/org/example/file2.csv", conn);
            System.out.println("Added successfully from csv file");

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertFromTxt(String path, Connection conn) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 3) {
                    insertRecord(Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]), conn);
                }
            }
        }
    }

    private static void insertFromCsv(String path, Connection conn) throws IOException, SQLException {
        String sql = "INSERT INTO ReadFile(id, name, age) VALUES (?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(path));
             PreparedStatement st = conn.prepareStatement(sql)) {

            boolean skipHeader = true;
            String line;
            while ((line = br.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }

                String[] p = line.split(",");
                if (p.length >= 3) {
                    st.setInt   (1, Integer.parseInt(p[0]));
                    st.setString(2, p[1]);
                    st.setInt   (3, Integer.parseInt(p[2]));
                    st.addBatch();
                }
            }
            st.executeBatch();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void insertRecord(int id, String name, int age, Connection conn) {
        String sql = "INSERT INTO ReadFile(id, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt   (1, id);
            st.setString(2, name);
            st.setInt   (3, age);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }
}

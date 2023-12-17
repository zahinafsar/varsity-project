package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Voter;
import Utils.Database;

public class UserDB {
    // public static Voter users[] = {
    // new Voter("1", "Rahim", "./", "01234"),
    // new Voter("2", "Sarwar", "./", "34556"),
    // new Voter("3", "Mursalin", "./", "56785"),
    // new Voter("4", "Mostak", "./", "67655"),
    // };

    // public static int userCount = 3;
    // public static int id = 4;

    public static Voter users[] = new Voter[100];
    public static int userCount = 0;
    public static int id = 1;

    public static ResultSet getVoters() {
        Connection con = Database.getConnection();
        try {
            String sql = "select * from users";
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void addVoter(Voter user) {
        Connection con = Database.getConnection();
        try {
            String sql = "insert into users(name, phone, code) values(?, ?, ?)";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, user.getName());
            p.setString(2, user.getPhone());
            p.setString(3, user.getVoterCode());
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static String getNextId() {
        return String.valueOf(id);
    }

    public static void updateVoter(String id, String name, String phone) {
        Connection con = Database.getConnection();
        try {
            String sql = "UPDATE users SET name = ?, phone = ? WHERE id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, name);
            p.setString(2, phone);
            p.setInt(3, Integer.parseInt(id));
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteVoter(String id) {
        Connection con = Database.getConnection();
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, Integer.parseInt(id));
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

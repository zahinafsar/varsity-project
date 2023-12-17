package Database;

import java.security.cert.TrustAnchor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Candidate;
import Utils.Database;
import Utils.RandomAvatar;

public class CandidateDB {
    public static Candidate users[] = new Candidate[100];
    public static int userCount = 0;
    public static int id = 1;

    public static ResultSet getCandidates() {
        Connection con = Database.getConnection();
        try {
            String sql = "select * from candidates";
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void addCandidate(Candidate user) {
        // for (int i = 0; i < users.length; i++) {
        // if (users[i] == null) {
        // users[i] = user;
        // userCount++;
        // id++;
        // break;
        // }
        // }
        Connection con = Database.getConnection();
        try {
            String sql = "insert into candidates(name, phone, image) values(?, ?, ?)";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, user.getName());
            p.setString(2, user.getPhone());
            p.setString(3, user.getImage());
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean isValidSymbol(String image) {
        boolean valid = false;
        for (int index = 1; index <= 25; index++) {
            if (image.equals(Integer.toString(index))) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public static boolean isOpenSymbol(String image) {
        boolean valid = true;
        Connection con = Database.getConnection();
        try {
            String sql = "select * from candidates where image = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, image);
            p.executeQuery();
            valid = !p.getResultSet().next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return valid;
    }

    public static String getNextId() {
        return String.valueOf(id);
    }

    public static void updateCandidate(String id, String name, String phone, String symbol) {
        // for (int i = 0; i < users.length; i++) {
        // if (users[i].getId().equals(id)) {
        // users[i].setName(name);
        // users[i].setPhone(phone);
        // users[i].setImage(symbol);
        // break;
        // }
        // }
        Connection con = Database.getConnection();
        try {
            String sql = "UPDATE candidates SET name = ?, phone = ?, image = ? WHERE id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, name);
            p.setString(2, phone);
            p.setString(3, symbol);
            p.setInt(4, Integer.parseInt(id));
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteCandidate(String id) {
        // for (int i = 0; i < users.length; i++) {
        // if (users[i].getId().equals(id)) {
        // users[i] = null;
        // userCount--;
        // for (int j = i; j < users.length - 1; j++) {
        // users[j] = users[j + 1];
        // }
        // break;
        // }
        // }

        Connection con = Database.getConnection();
        try {
            String sql = "DELETE FROM candidates WHERE id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, Integer.parseInt(id));
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static Candidate getCandidate(String id) {
        // for (int i = 0; i < users.length; i++) {
        // if (users[i].getId().equals(id)) {
        // return users[i];
        // }
        // }
        // return null;
        Connection con = Database.getConnection();
        try {
            String sql = "select * from candidates where id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, Integer.parseInt(id));
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                Candidate user = new Candidate(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("phone"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}

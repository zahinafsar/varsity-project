package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Vote;
import Model.Voter;
import Utils.Database;

public class VoteDB {
    // public static Vote votes[] = {
    // new Vote("1", "1", "1", "1")
    // };
    public static Vote votes[] = new Vote[100];
    public static int userCount = 0;
    public static int id = 1;

    public static boolean idValidCode(String code) {
        // boolean valid = false;
        // for (int i = 0; i < UserDB.userCount; i++) {
        // Voter user = UserDB.users[i];
        // if (user.getVoterCode().equals(code)) {
        // valid = true;
        // break;
        // }
        // }
        // return valid;
        boolean valid = false;
        Connection con = Database.getConnection();
        try {
            String sql = "select * from users where code = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, code);
            p.executeQuery();
            valid = p.getResultSet().next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return valid;
    }

    public static boolean idValidVote(String code, String event_id) {
        // boolean valid = true;
        // for (int i = 0; i < votes.length; i++) {
        // if (votes[i] != null) {
        // if (votes[i].getVoterCode().equals(code) &&
        // votes[i].getEventId().equals(event_id)) {
        // valid = false;
        // break;
        // }
        // }
        // }
        // return valid;
        boolean valid = true;
        Connection con = Database.getConnection();
        try {
            String sql = "select * from votes where code = ? and event_id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, code);
            p.setString(2, event_id);
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

    public static void addVote(Vote vote) {
        // for (int i = 0; i < votes.length; i++) {
        // if (votes[i] == null) {
        // votes[i] = vote;
        // userCount++;
        // id++;
        // break;
        // }
        // }
        Connection con = Database.getConnection();
        try {
            String sql = "insert into votes(candidate_id, code, event_id) values(?, ?, ?)";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, vote.getCandidateId());
            p.setString(2, vote.getVoterCode());
            p.setString(3, vote.getEventId());
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static int getNumberOfVotes(String candidate_id, String event_id) {
        // int count = 0;
        // for (int i = 0; i < votes.length; i++) {
        // if (votes[i] != null) {
        // if (votes[i].getCandidateId().equals(candidate_id) &&
        // votes[i].getEventId().equals(event_id)) {
        // count++;
        // }
        // }
        // }
        // return count;
        Connection con = Database.getConnection();
        int count = 0;
        try {
            String sql = "select count(*) from votes where candidate_id = ? and event_id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, candidate_id);
            p.setString(2, event_id);
            // p.executeQuery();
            // count = p.getResultSet().getInt(1);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return count;
    }
}

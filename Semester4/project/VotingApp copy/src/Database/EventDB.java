package Database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Candidate;
import Model.Event;
import Utils.Database;

public class EventDB {
    // public static Event[] events = {
    // new Event("1", "Event 1", new String[] { "1", "2", "3" }),
    // new Event("2", "Event 2", new String[] { "1", "3", "4" }),
    // new Event("3", "Event 3", new String[] { "2", "3" }),
    // new Event("4", "Event 4", new String[] { "1", "2" }),
    // };
    // public static int eventCount = 3;
    // public static int id = 4;

    public static Event events[] = new Event[100];
    public static int eventCount = 0;
    public static int id = 1;

    public static ResultSet getEvents() {
        Connection con = Database.getConnection();
        try {
            String sql = "select * from events";
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void addEvent(Event event) {
        // for (int i = 0; i < events.length; i++) {
        // if (events[i] == null) {
        // events[i] = event;
        // eventCount++;
        // id++;
        // break;
        // }
        // }
        Connection con = Database.getConnection();
        try {
            String sql = "insert into events(name, candidates) values(?, ARRAY[?])";
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, event.getName());
            // String candidatesString = String.join(",", event.getCandidates());
            // p.setString(2, candidatesString);
            p.setObject(2, event.getCandidates(), java.sql.Types.ARRAY);
            p.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static String getNextId() {
        return String.valueOf(id);
    }

    public static Event getEvent(String id) {
        // for (int i = 0; i < events.length; i++) {
        // if (events[i].getId().equals(id2)) {
        // return events[i];
        // }
        // }
        // return null;
        Connection con = Database.getConnection();
        try {
            String sql = "select * from events where id = ?";
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, Integer.parseInt(id));
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String candidatesString = rs.getString("candidates");
                String trimmedInput = candidatesString.substring(2, candidatesString.length() - 2);
                String[] candidates = trimmedInput.split(",");

                // Candidate[] candidates = new Candidate[elements.length];
                // for (String candidate : elements) {
                //     Candidate c = CandidateDB.getCandidate(candidate);
                //     if (c != null) {
                //         for (int i = 0; i < candidates.length; i++) {
                //             if (candidates[i] == null) {
                //                 candidates[i] = c;
                //                 break;
                //             }
                //         }
                //     }
                // }
                // System.out.println(candidatesString.getArray());
                Event event = new Event(id, name, candidates);
                return event;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}

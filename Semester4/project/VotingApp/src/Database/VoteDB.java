package Database;

import Model.Vote;
import Model.Voter;

public class VoteDB {
    // public static Vote votes[] = {
    // new Vote("1", "1", "1", "1")
    // };
    public static Vote votes[] = new Vote[100];
    public static int userCount = 0;
    public static int id = 1;

    public static boolean idValidCode(String code) {
        boolean valid = false;
        for (int i = 0; i < UserDB.userCount; i++) {
            Voter user = UserDB.users[i];
            if (user.getVoterCode().equals(code)) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public static boolean idValidVote(String code, String eventId) {
        boolean valid = true;
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] != null) {
                if (votes[i].getVoterCode().equals(code) && votes[i].getEventId().equals(eventId)) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    public static String getNextId() {
        return String.valueOf(id);
    }

    public static void addVote(Vote vote) {
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] == null) {
                votes[i] = vote;
                userCount++;
                id++;
                break;
            }
        }
    }

    public static int getNumberOfVotes(String candidateId, String eventId) {
        int count = 0;
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] != null) {
                if (votes[i].getCandidateId().equals(candidateId) && votes[i].getEventId().equals(eventId)) {
                    count++;
                }
            }
        }
        return count;
    }
}

package Database;

import Model.Candidate;
import Utils.RandomAvatar;

public class CandidateDB {
    // public static Candidate users[] = new Candidate[100];
    public static Candidate users[] = {
            new Candidate("1", "Zahin Afsar", "src/Assets/1.png", "23452345"),
            new Candidate("2", "Nurul Huda", "src/Assets/2.png", "23452345"),
            new Candidate("3", "Jubayer Al Mamun", "src/Assets/3.png", "23452345"),
            new Candidate("4", "Shamim", "src/Assets/4.png", "23452345")
    };
    public static int userCount = 3;
    public static int id = 4;

    public static void addCandidate(Candidate user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                userCount++;
                id++;
                break;
            }
        }
    }

    public static String getNextId() {
        return String.valueOf(id);
    }

    public static void updateCandidate(String id, String name, String phone, String symbol) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id)) {
                users[i].setName(name);
                users[i].setPhone(phone);
                users[i].setImage(symbol);
                break;
            }
        }
    }

    public static void deleteCandidate(String id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id)) {
                users[i] = null;
                userCount--;
                for (int j = i; j < users.length - 1; j++) {
                    users[j] = users[j + 1];
                }
                break;
            }
        }
    }

    public static Candidate getCandidate(String id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id)) {
                return users[i];
            }
        }
        return null;
    }
}

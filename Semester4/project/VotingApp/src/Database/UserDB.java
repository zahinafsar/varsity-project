package Database;

import Model.Voter;

public class UserDB {
    // public static Voter users[] = new Voter[100];
    public static Voter users[] = {
        new Voter("1", "Rahim", "./", "01234"),
        new Voter("2", "Sarwar", "./", "34556"),
        new Voter("3", "Mursalin", "./", "56785"),
        new Voter("4", "Mostak", "./", "67655"),
    };

    public static int userCount = 3;
    public static int id = 4;

    public static void addVoter(Voter user) {
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

    public static void updateVoter(String id, String name, String phone) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id)) {
                users[i].setName(name);
                users[i].setPhone(phone);
                break;
            }
        }
    }

    public static void deleteVoter(String id) {
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
}

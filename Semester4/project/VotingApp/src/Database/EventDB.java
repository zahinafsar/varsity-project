package Database;

import Model.Event;

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

    public static void addEvent(Event event) {
        for (int i = 0; i < events.length; i++) {
            if (events[i] == null) {
                events[i] = event;
                eventCount++;
                id++;
                break;
            }
        }
    }

    public static String getNextId() {
        return String.valueOf(id);
    }

    public static Event getEvent(String id2) {
        for (int i = 0; i < events.length; i++) {
            if (events[i].getId().equals(id2)) {
                return events[i];
            }
        }
        return null;
    }
}

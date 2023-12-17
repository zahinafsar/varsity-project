package Model;

public class Event {
    private String id;
    private String name;
    private String[] candidates;

    public Event(String id, String name, String[] candidates) {
        this.id = id;
        this.name = name;
        this.candidates = candidates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCandidate(int index) {
        return candidates[index];
    }

    public String[] getCandidates() {
        return candidates;
    }

    public void setName(String name) {
        this.name = name;
    }

}

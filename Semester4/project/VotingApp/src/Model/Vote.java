package Model;

import Database.VoteDB;

public class Vote {
    private String id;
    private String candidate_id;
    private String code;
    private String event_id;

    public Vote(String candidate_id, String code, String event_id) {
        this.id = VoteDB.getNextId();
        this.candidate_id = candidate_id;
        this.code = code;
        this.event_id = event_id;
    }

    public String getId() {
        return id;
    }

    public String getCandidateId() {
        return candidate_id;
    }

    public String getVoterCode() {
        return code;
    }

    public String getEventId() {
        return event_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCandidateId(String candidate_id) {
        this.candidate_id = candidate_id;
    }

    public void setVoterCode(String code) {
        this.code = code;
    }

    public void setEventId(String event_id) {
        this.event_id = event_id;
    }
}

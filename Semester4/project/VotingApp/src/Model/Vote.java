package Model;

import Database.VoteDB;

public class Vote {
    private String id;
    private String candidateId;
    private String code;
    private String eventId;

    public Vote(String candidateId, String code, String eventId) {
        this.id = VoteDB.getNextId();
        this.candidateId = candidateId;
        this.code = code;
        this.eventId = eventId;
    }

    public String getId() {
        return id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getVoterCode() {
        return code;
    }

    public String getEventId() {
        return eventId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public void setVoterCode(String code) {
        this.code = code;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

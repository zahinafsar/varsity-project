package Model;

import Database.VoteDB;

public class Vote {
    private String id;
    private String candidateId;
    private String voterCode;
    private String eventId;

    public Vote(String candidateId, String voterCode, String eventId) {
        this.id = VoteDB.getNextId();
        this.candidateId = candidateId;
        this.voterCode = voterCode;
        this.eventId = eventId;
    }

    public String getId() {
        return id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getVoterCode() {
        return voterCode;
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

    public void setVoterCode(String voterCode) {
        this.voterCode = voterCode;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

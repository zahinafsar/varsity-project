package Model;

public class Voter extends User {
    private String voterCode;

    public Voter(String _id, String _name, String _image, String _phone) {
        super(_id, _name, _image, _phone);
        this.voterCode = Integer.toString((int) (Math.random() * 9000) + 1000);
    }

    public String getVoterCode() {
        return voterCode;
    }
}

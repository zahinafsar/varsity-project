package Model;

public class User {
    private String id;
    private String name;
    private String phone;
    private String image;

    User(String _id, String _name, String _image, String _phone) {
        this.id = _id;
        this.name = _name;
        this.image = _image;
        this.phone = _phone;
    }

    public String getId() {
        return id;
    }
    public void setId(String _id) {
        this.id = _id;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String _image) {
        this.image = _image;
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        this.name = _name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String _phone) {
        this.phone = _phone;
    }
}

package Model;

public class User {
    private String id;
    private String name;
    private String image;
    User(String _id, String _name, String _image) {
        this.id = _id;
        this.name = _name;
        this.image = _image;
    }
    public String getId() {
        return id;
    }
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
}

package Utils;

import java.net.MalformedURLException;
// import java.net.URL;

public class RandomAvatar {
    public String getAvatar() throws MalformedURLException {
        int randomNumber = (int) (Math.random() * 25) + 1;
        // String avatar = "https://robohash.org/" + Math.random();
        // URL url = new URL(avatar);
        // return url;
        return "./Assets/" + randomNumber + ".png";
    }
}

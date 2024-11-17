import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class report1 {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://webcode.me/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Chrome");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code " + responseCode);
        System.out.println("Response Message " + con.getResponseMessage());
        if(responseCode == HttpURLConnection.HTTP_OK) {
            InputStreamReader in = new InputStreamReader(con.getInputStream());
            BufferedReader read = new BufferedReader(in);
            String str = null;
            while((str = read.readLine()) != null) {
                System.out.println(str);
            }
        } else {
            System.out.println("GET method not worked!");
        }
    }
}

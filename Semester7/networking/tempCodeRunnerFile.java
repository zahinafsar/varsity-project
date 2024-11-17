import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class report2 {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Chrome");
        con.setDoOutput(true);
        String msg = "This is my post";
        OutputStream output = con.getOutputStream();
        output.write(msg.getBytes());
        output.flush();
        output.close();
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        System.out.println("Response Message: " + con.getResponseMessage());
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            InputStreamReader in = new InputStreamReader(con.getInputStream());
            BufferedReader read = new BufferedReader(in);
            String str;
            while ((str = read.readLine()) != null) {
                System.out.println(str);
            }
            read.close();
        } else {
            System.out.println("Post method did not work!");
        }
    }
}

package marxbank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class UrlHandler {
    private final static String RESTURLSTRING = "http://localhost:8080";
    private static URL apiURL;

    public static String handleGet(String path) {

        String url = String.format("%s%s", RESTURLSTRING, path);

        try {
            apiURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed with http code: " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String output = br.lines().collect(Collectors.joining());

            connection.disconnect();

            return output;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    public static String handlePost(String path, String data) {

        String url = String.format("%s%s", RESTURLSTRING, path);
        try {
            apiURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            // if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            //     throw new RuntimeException("Failed with http code: " + connection.getResponseCode());
            // }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String output = br.lines().collect(Collectors.joining());

            connection.disconnect();

            return output;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}

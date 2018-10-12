import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class QueryHttpAPI {

    String urlString = "http://localhost:9090/api/v1/";

    public void requestQuery(String query) {

        this.urlString += query;

        String out = "";

        try {
            URL url = new URL(this.urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();
            System.out.println("Response code is: " + response);

            if (response != 200)
                throw new RuntimeException("HttpResponseCode: " + response);
            else {
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    out += scanner.nextLine();
                }

                System.out.println("The result is : ");
                System.out.println(out);
                scanner.close();
            }


            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
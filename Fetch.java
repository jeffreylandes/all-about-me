import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Fetch {

    private final String DEFAULT_URL = "https://api.weather.gov/points/38.8894,-77.0352";
    private final String USER_AGENT = "personal-project, landesjeffrey@gmail.com";

    void printError(String message) {
        System.out.println(message);
    }
    void fetchWeather() {
        try {
            URL obj = new URL(DEFAULT_URL);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                conn.disconnect();
                
                System.out.println(response.toString());
            } else {
                throw new Error("Unsuccessful weather fetch.");
            }
        } catch (MalformedURLException e) {
            String errorMessage = String.format("Encountered malformed url: %s", DEFAULT_URL);
            printError(errorMessage);
        } catch (IOException e) {
            String errorMessage = "Encountered IO exception trying to establish connection.";
            printError(errorMessage);
        }
    }
    public static void main(String... main) {
        Fetch fetch = new Fetch();
        fetch.fetchWeather();
    }
}
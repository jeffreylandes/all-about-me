package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherFetch {

    private static final String BASE_FETCH_URL = "https://archive-api.open-meteo.com/v1/era5?";
    private static final String LOCATION_PARAM = "&timezone=Europe/Berlin";
    private static final String FREQUENCY_PARAM = "&daily=temperature_2m_max,temperature_2m_min";

    private float latitude = 0.0f;
    private float longitude = 0.0f;

    public WeatherFetch() {}

    public WeatherFetch(Location location) {
        this.latitude = location.latitude();
        this.longitude = location.longitude();
    } 

    public WeatherFetch(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    } 

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    private static String getLatitudeParam(Float startDate) {
        return "&latitude=" + startDate;
    }

    private static String getLongitudeParam(Float startDate) {
        return "&longitude=" + startDate;
    }

    private static String getStartDateParam(String startDate) {
        return "start_date=" + startDate;
    }

    private static String getEndDateParam(String startDate) {
        return "&end_date=" + startDate;
    }

    private URL buildFinalUrl(String startDate, String endDate) throws MalformedURLException {
        String latitudeUrl = getLatitudeParam(this.latitude);
        String longitudeUrl = getLongitudeParam(this.longitude);
        String urlString = BASE_FETCH_URL + 
            startDate + endDate + 
            latitudeUrl + longitudeUrl + 
            FREQUENCY_PARAM + LOCATION_PARAM;
        System.out.print("Final url: " + urlString);
        URL url = new URL(urlString);
        return url;
    }

    public static String getFullString(int day) {
        if (day < 10) {
            return "0" + String.valueOf(day);
        }
        return String.valueOf(day);
    }

    public void fetchWeatherFromDay(int year, int month, int day) {
        String yearString = String.valueOf(year);
        String monthString = getFullString(month);
        String dayString = getFullString(day);
        String startDate = yearString + "-" + monthString + "-" + dayString;
        // TODO: check month overflow
        String endDate = yearString + "-" + monthString + "-" + dayString;
        
        String startDateUrlParam = getStartDateParam(startDate);
        String endDateUrlParam = getEndDateParam(endDate);

        try {
            URL fetchUrl = buildFinalUrl(startDateUrlParam, endDateUrlParam);
            HttpURLConnection conn = (HttpURLConnection) fetchUrl.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) throw new Error("Unsuccessful request");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.print(response);

        } catch (MalformedURLException e) {
            System.out.print("Unable to build fetch url: " + e);
        } catch (IOException e) {
            System.out.print("Encountered url exception: " + e);
        }
    }

    public static void main(String... args) {
        Location location = new Location(52.5f, 13.5f);
        WeatherFetch weatherFetch = new WeatherFetch(location);
        weatherFetch.fetchWeatherFromDay(2021, 1, 30);
    }
}

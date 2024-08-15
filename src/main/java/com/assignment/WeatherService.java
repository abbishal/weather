package com.assignment;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherService {

    private static final String API_KEY = "46280e9a0dab85b46ef10c0df6c8463c";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s";
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&cnt=5&appid=%s";

    public static JsonObject getWeatherData(String location, String units) throws Exception {
        String urlString = String.format(WEATHER_API_URL, location, units, API_KEY);
        return fetchData(urlString);
    }

    public static JsonObject getForecastData(String location, String units) throws Exception {
        String urlString = String.format(FORECAST_API_URL, location, units, API_KEY);
        return fetchData(urlString);
    }

    private static JsonObject fetchData(String urlString) throws Exception {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
        JsonElement jsonElement = JsonParser.parseReader(reader);
        reader.close();
        return jsonElement.getAsJsonObject();
    }
}


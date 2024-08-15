package com.assignment;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WeatherApp extends Application {

    private TextField locationField;
    private Label weatherLabel;
    private Label forecastLabel;
    private ImageView weatherIcon;
    private ComboBox<String> unitComboBox;
    private ListView<String> historyList;
    private String units = "metric"; // Default to Celsius

    private List<String> searchHistory = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Information App");

        // Layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Dynamic Background
        setDynamicBackground(root);

        // Top Section - Input and Search
        HBox topSection = new HBox(10);
        topSection.setAlignment(Pos.CENTER);

        Label locationLabel = new Label("Enter Location:");
        locationField = new TextField();
        locationField.setPromptText("City name");

        Button getWeatherButton = new Button("Get Weather");
        getWeatherButton.setOnAction(e -> getWeather());

        unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("Celsius", "Fahrenheit");
        unitComboBox.setValue("Celsius");
        unitComboBox.setOnAction(e -> switchUnits());

        topSection.getChildren().addAll(locationLabel, locationField, unitComboBox, getWeatherButton);

        // Center Section - Weather Information
        VBox centerSection = new VBox(10);
        centerSection.setAlignment(Pos.CENTER);

        weatherLabel = new Label("Weather information will be displayed here.");
        weatherLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: white; -fx-padding: 10px; -fx-font-size: 18px;");

        forecastLabel = new Label("Forecast information will be displayed here.");
        forecastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: white; -fx-padding: 10px; -fx-font-size: 14px;");
        weatherIcon = new ImageView();
        weatherIcon.setFitHeight(100);
        weatherIcon.setFitWidth(100);
        

        centerSection.getChildren().addAll(weatherIcon, weatherLabel, forecastLabel);

        // Right Section - History
        VBox rightSection = new VBox(10);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPadding(new Insets(10));

        Label historyLabel = new Label("Search History:");
        historyList = new ListView<>();

        rightSection.getChildren().addAll(historyLabel, historyList);

        // Set Layouts
        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setRight(rightSection);

        // Scene
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void getWeather() {
        String location = locationField.getText();
        if (location.isEmpty()) {
            showAlert("Error", "Please enter a location.");
            return;
        }

        try {
            JsonObject weatherData = WeatherService.getWeatherData(location, units);
            JsonObject forecastData = WeatherService.getForecastData(location, units);
            updateWeatherInfo(weatherData, forecastData);

            // Update search history
            String historyEntry = String.format("%s - %s", location, LocalTime.now().toString());
            searchHistory.add(historyEntry);
            historyList.getItems().add(historyEntry);

        } catch (Exception ex) {
            showAlert("Error", "Failed to fetch weather data. Please check the location and try again.");
        }
    }

    private void updateWeatherInfo(JsonObject weatherData, JsonObject forecastData) {
        JsonObject main = weatherData.getAsJsonObject("main");
        double temp = main.get("temp").getAsDouble();
        int humidity = main.get("humidity").getAsInt();
        String weather = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        String iconCode = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";

        // Update Weather Information
        weatherLabel.setText(String.format("Temperature: %.2f°C\nHumidity: %d%%\nCondition: %s", temp, humidity, weather));
        weatherIcon.setImage(new Image(iconUrl));

        // Update Forecast Information
        StringBuilder forecastBuilder = new StringBuilder("Forecast:\n");
        forecastData.getAsJsonArray("list").forEach(item -> {
            JsonObject forecastItem = item.getAsJsonObject();
            String forcastTime = forecastItem.get("dt_txt").getAsString();
            double forecastTemp = forecastItem.getAsJsonObject("main").get("temp").getAsDouble();
            String forecastCondition = forecastItem.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            forecastBuilder.append(String.format("Time: %s \tTemp: %.2f°C, Condition: %s\n",forcastTime, forecastTemp, forecastCondition));
        });

        forecastLabel.setText(forecastBuilder.toString());
    }

    private void switchUnits() {
        if (unitComboBox.getValue().equals("Celsius")) {
            units = "metric";
        } else {
            units = "imperial";
        }
        getWeather(); // Refresh weather info with new units
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setDynamicBackground(BorderPane root) {
        Image backgroundImage;
        LocalTime now = LocalTime.now();
    
        if (now.isBefore(LocalTime.NOON) && now.isAfter(LocalTime.of(5, 0))) {
            backgroundImage = new Image("afternoon.jpg");
        } else if (now.isBefore(LocalTime.of(18, 0)) && now.isAfter(LocalTime.NOON)) {
            backgroundImage = new Image("afternoon.jpg");
        } else if (now.isBefore(LocalTime.of(22, 0)) && now.isAfter(LocalTime.of(18, 0))) {
            backgroundImage = new Image("evening.jpg");
        } else {
            backgroundImage = new Image("night.jpg");
        }
    
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        root.setBackground(new Background(background));
    }
    
}

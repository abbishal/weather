
# Weather Information App

## Overview

The Weather Information App is a Java-based application that provides real-time weather updates for a specified location. The app fetches weather data from the OpenWeatherMap API and displays it in a user-friendly graphical interface built using JavaFX. It includes features such as temperature display, weather icons, short-term forecasts, unit conversion, error handling, history tracking, and dynamic backgrounds based on the time of day.

## Features

- **Real-time Weather Data**: Fetches and displays current weather information, including temperature, humidity, wind speed, and weather conditions.
- **Weather Icons**: Visual representation of weather conditions using icons.
- **Short-Term Forecast**: Displays a short-term weather forecast for the selected location.
- **Unit Conversion**: Allows switching between Celsius and Fahrenheit for temperature, and between different units for wind speed.
- **Error Handling**: Handles invalid location inputs and failed API requests with user-friendly error messages.
- **History Tracking**: Keeps a history of recent weather searches with timestamps.
- **Dynamic Backgrounds**: Changes the background image based on the time of day (morning, afternoon, evening).

## Installation

### Prerequisites

- **Java Development Kit (JDK) 17 or higher**: Ensure that Java is installed and the environment variable `JAVA_HOME` is set.
- **Maven**: Ensure that Maven is installed to manage project dependencies and build the project.

### Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/weather-app.git
   cd weather-app
   ```

2. **Build the Project**
   Use Maven to clean and package the project:
   ```bash
   mvn clean package
   ```

3. **Run the Application**
   Run the application directly using the Maven JavaFX plugin:
   ```bash
   mvn javafx:run
   ```

   Alternatively, you can run the JAR file:
   ```bash
   java --module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls -jar target/weather-1.0-SNAPSHOT.jar
   ```

   Replace `/path/to/javafx-sdk-17/lib` with the actual path to your JavaFX SDK.

## Usage

- **Enter a Location**: Type the name of the city or location in the input field and click "Get Weather" to fetch and display weather data.
- **View Weather Details**: The app will display the current temperature, humidity, wind speed, and weather conditions, along with a relevant weather icon.
- **Switch Units**: Use the dropdown to switch between Celsius and Fahrenheit for temperature.
- **View Forecast**: A short-term forecast will be displayed for the selected location.
- **Check History**: The right section of the app will show a history of recent searches with timestamps.
- **Dynamic Background**: The background will change based on the time of day.

## Implementation Details

- **Main Class**: `com.assignment.weather.WeatherApp`
  - This class is the entry point of the application and is responsible for setting up the JavaFX GUI and handling user interactions.

- **Weather Service**: `com.assignment.weather.WeatherService`
  - This class handles communication with the OpenWeatherMap API, fetching weather data and parsing the JSON response.

- **Dependencies**:
  - **JavaFX**: For building the graphical user interface.
  - **Gson**: For parsing JSON responses from the weather API.

- **Project Structure**:
  ```
  src/
  ├── main/
  │   ├── java/
  │   │   └── com/
  │   │       └── assignment/
  │   │           └── weather/
  │   │               ├── WeatherApp.java
  │   │               └── WeatherService.java
  │   └── resources/
  └── test/
  ```

## Customization

- **Background Images**: You can replace the default background images for morning, afternoon, and evening by placing new images in the `resources` folder and updating the image paths in the `WeatherApp.java` file.

- **API Key**: Replace the placeholder API key in `WeatherService.java` with your own from OpenWeatherMap.

## Troubleshooting

### Common Errors

- **`NoClassDefFoundError: javafx/application/Application`**: Ensure that JavaFX is correctly added to your module path or use the Maven JavaFX plugin to run the project.


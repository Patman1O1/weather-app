package edu.cs342.project2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Weather {
    /* ------------------------------------------------Daily Forecast------------------------------------------------ */
    public static class DailyForecast {
        /* -----------------------------------------------Fields----------------------------------------------------- */
        private final String time;

        private final double maxTemp,
                             minTemp,
                             maxApparentTemp,
                             minApparentTemp,
                             precipSum,
                             rainSum,
                             snowSum,
                             precipHours;

        private final int precipProbMax, precipProbMean;

        private final WeatherCode weatherCode;

        /* ---------------------------------------------Constructors------------------------------------------------- */
        private DailyForecast(JSONObject jsonObject, int index) {
            // Set string fields
            JSONArray timeArray = jsonObject.getJSONArray("time");
            this.time = timeArray.isNull(index) ? null : timeArray.getString(index);


            // Set double fields
            JSONArray  maxTempArray = jsonObject.getJSONArray("temperature_2m_max");
            this.maxTemp = maxTempArray.isNull(index) ? 0.0 : maxTempArray.getDouble(index);

            JSONArray minTempArray = jsonObject.getJSONArray("temperature_2m_min");
            this.minTemp = minTempArray.isNull(index) ? 0.0 : minTempArray.getDouble(index);

            JSONArray maxApparentTempArray = jsonObject.getJSONArray("apparent_temperature_max");
            this.maxApparentTemp = maxApparentTempArray.isNull(index) ? 0.0 : maxApparentTempArray.getDouble(index);

            JSONArray minApparentTempArray = jsonObject.getJSONArray("apparent_temperature_min");
            this.minApparentTemp = minApparentTempArray.isNull(index) ? 0.0 : minApparentTempArray.getDouble(index);

            JSONArray precipSumArray = jsonObject.getJSONArray("precipitation_sum");
            this.precipSum = precipSumArray.isNull(index) ? 0.0 : precipSumArray.getDouble(index);

            JSONArray rainSumArray = jsonObject.getJSONArray("rain_sum");
            this.rainSum = rainSumArray.isNull(index) ? 0.0 : rainSumArray.getDouble(index);

            JSONArray snowSumArray = jsonObject.getJSONArray("snowfall_sum");
            this.snowSum = snowSumArray.isNull(index) ? 0.0 : snowSumArray.getDouble(index);

            JSONArray precipHoursArray = jsonObject.getJSONArray("precipitation_hours");
            this.precipHours = precipHoursArray.isNull(index) ? 0.0 : precipHoursArray.getDouble(index);


            // Set integer fields
            JSONArray precipProbMaxArray = jsonObject.getJSONArray("precipitation_probability_max");
            this.precipProbMax = precipProbMaxArray.isNull(index) ? 0 : precipProbMaxArray.getInt(index);

            JSONArray precipProbMeanArray = jsonObject.getJSONArray("precipitation_probability_mean");
            this.precipProbMean = precipProbMeanArray.isNull(index) ? 0 : precipProbMeanArray.getInt(index);


            // Set weather code
            JSONArray weatherCodeArray = jsonObject.getJSONArray("weathercode");
            this.weatherCode = weatherCodeArray.isNull(index) ? null : WeatherCode.get(weatherCodeArray.getInt(index));
        }

        /* -----------------------------------------------Getters---------------------------------------------------- */
        public String getTime() { return this.time; }

        public double getMaxTemp() { return this.maxTemp; }

        public double getMinTemp() { return this.minTemp; }

        public double getMaxApparentTemp() { return this.maxApparentTemp; }

        public double getMinApparentTemp() { return this.minApparentTemp; }

        public double getPrecipSum() { return this.precipSum; }

        public double getRainSum() { return this.rainSum; }

        public double getSnowSum() { return this.snowSum; }

        public double getPrecipHours() { return this.precipHours; }

        public int getPrecipProbMax() { return this.precipProbMax; }

        public int getPrecipProbMean() { return this.precipProbMean; }

        public WeatherCode getWeatherCode() { return this.weatherCode; }

    }

    /* -----------------------------------------------Hourly Forecast------------------------------------------------ */
    public static class HourlyForecast {
        /* -----------------------------------------------Fields----------------------------------------------------- */
        private final String time;

        private final double temperature,
                             apparentTemperature,
                             dewPoint,
                             precipitation,
                             snowfall,
                             snowDepth,
                             windSpeed,
                             windGusts,
                             windDirection,
                             pressure,
                             visibility,
                             uvIndex,
                             freezingLevelHeight;

        private final int humidity, precipProb;

        private final WeatherCode weatherCode;

        /* --------------------------------------------Constructors-------------------------------------------------- */
        private HourlyForecast(JSONObject jsonObject, int index) {
            // Set string fields
            JSONArray timeArray = jsonObject.getJSONArray("time");
            this.time = timeArray.isNull(index) ? null : timeArray.getString(index);


            // Set double fields
            JSONArray temperatureArray = jsonObject.getJSONArray("temperature_2m");
            this.temperature = temperatureArray.isNull(index) ? 0.0 : temperatureArray.getDouble(index);

            JSONArray apparentTemperatureArray = jsonObject.getJSONArray("apparent_temperature");
            this.apparentTemperature = apparentTemperatureArray.isNull(index) ? 0.0 : apparentTemperatureArray.getDouble(index);

            JSONArray dewPointArray = jsonObject.getJSONArray("dewpoint_2m");
            this.dewPoint = dewPointArray.isNull(index) ? 0.0 : dewPointArray.getDouble(index);

            JSONArray precipitationArray = jsonObject.getJSONArray("precipitation");
            this.precipitation = precipitationArray.isNull(index) ? 0.0 : precipitationArray.getDouble(index);

            JSONArray snowfallArray = jsonObject.getJSONArray("snowfall");
            this.snowfall =  snowfallArray.isNull(index) ? 0.0 : snowfallArray.getDouble(index);

            JSONArray snowDepthArray = jsonObject.getJSONArray("snow_depth");
            this.snowDepth = snowDepthArray.isNull(index) ? 0.0 : snowDepthArray.getDouble(index);

            JSONArray windSpeedArray = jsonObject.getJSONArray("windspeed_10m");
            this.windSpeed = windSpeedArray.isNull(index) ? 0.0 : windSpeedArray.getDouble(index);

            JSONArray windDirectionArray = jsonObject.getJSONArray("winddirection_10m");
            this.windDirection = windDirectionArray.isNull(index) ? 0.0 : windDirectionArray.getDouble(index);

            JSONArray windGustsArray = jsonObject.getJSONArray("windgusts_10m");
            this.windGusts = windGustsArray.isNull(index) ? 0.0 : windGustsArray.getDouble(index);

            JSONArray pressureArray = jsonObject.getJSONArray("pressure_msl");
            this.pressure = pressureArray.isNull(index) ? 0.0 : pressureArray.getDouble(index);

            JSONArray visibilityArray = jsonObject.getJSONArray("visibility");
            this.visibility = visibilityArray.isNull(index) ? 0.0 : visibilityArray.getDouble(index);

            JSONArray uvIndexArray = jsonObject.getJSONArray("uv_index");
            this.uvIndex = uvIndexArray.isNull(index) ? 0.0 : uvIndexArray.getDouble(index);

            JSONArray freezingLevelHeightArray = jsonObject.getJSONArray("freezinglevel_height");
            this.freezingLevelHeight = freezingLevelHeightArray.isNull(index) ? 0.0 : freezingLevelHeightArray.getDouble(index);


            // Set integer fields
            JSONArray humidityArray = jsonObject.getJSONArray("relativehumidity_2m");
            this.humidity = humidityArray.isNull(index) ? 0 : humidityArray.getInt(index);

            JSONArray precipProbArray = jsonObject.getJSONArray("precipitation_probability");
            this.precipProb = precipProbArray.getInt(index);


            // Set weather code
            JSONArray weatherCodeArray = jsonObject.getJSONArray("weathercode");
            this.weatherCode = weatherCodeArray.isNull(index) ? null : WeatherCode.get(weatherCodeArray.getInt(index));
        }

        /* ----------------------------------------------Getters----------------------------------------------------- */
        public String getTime() { return this.time; }

        public double getTemperature() { return this.temperature; }

        public double getApparentTemperature() { return this.apparentTemperature; }

        public double getDewPoint() { return this.dewPoint; }

        public double getPrecipitation() { return this.precipitation; }

        public double getSnowfall() { return this.snowfall; }

        public double getSnowDepth() { return this.snowDepth; }

        public double getWindSpeed() { return this.windSpeed; }

        public double getWindGusts() { return this.windGusts; }

        public double getWindDirection() { return this.windDirection; }

        public double getPressure() { return this.pressure; }

        public double getVisibility() { return this.visibility; }

        public double getUVIndex() { return this.uvIndex; }

        public double getFreezingLevelHeight() { return this.freezingLevelHeight; }

        public int getHumidity() { return this.humidity; }

        public int getPrecipProbability() { return this.precipProb; }

        public WeatherCode getWeatherCode() { return this.weatherCode; }
    }

    /* ---------------------------------------------------Fields----------------------------------------------------- */
    private final Location location;

    private double temperature,
                   dewPoint,
                   apparentTemperature,
                   precipitation,
                   windSpeed,
                   windDirection,
                   windGusts,
                   uvIndex,
                   visibility,
                   freezingLevel,
                   pressure;

    private int humidity, cloudCover;

    private boolean isDay;

    private WeatherCode weatherCode;

    private ArrayList<DailyForecast> dailyForecasts;

    private ArrayList<HourlyForecast> hourlyForecasts;

    /* ------------------------------------------------Constructors-------------------------------------------------- */
    public Weather() throws IOException, InterruptedException {
        this.location = new Location();

        JSONObject data = this.fetchData();

        this.setCurrentForecast(data);
        this.setDailyForecasts(data);
        this.setHourlyForecast(data);
    }

    /* --------------------------------------------------Setters----------------------------------------------------- */
    private void setCurrentForecast(JSONObject data) {
        JSONObject currentForecast = data.getJSONObject("current");

        // Set double fields
        this.temperature = currentForecast.getDouble("temperature_2m");
        this.dewPoint = currentForecast.getDouble("dewpoint_2m");
        this.apparentTemperature = currentForecast.getDouble("apparent_temperature");
        this.precipitation = currentForecast.getDouble("precipitation");
        this.windSpeed = currentForecast.getDouble("windspeed_10m");
        this.windDirection = currentForecast.getDouble("winddirection_10m");
        this.windGusts = currentForecast.getDouble("windgusts_10m");
        this.uvIndex = currentForecast.getDouble("uv_index");
        this.visibility = currentForecast.getDouble("visibility");
        this.freezingLevel = currentForecast.getDouble("freezinglevel_height");
        this.pressure = currentForecast.getDouble("surface_pressure");

        // Set integer fields
        this.humidity = currentForecast.getInt("relativehumidity_2m");
        this.cloudCover = currentForecast.getInt("cloudcover");

        // Set boolean fields
        this.isDay = currentForecast.getInt("is_day") == 1;

        // Set weather code
        this.weatherCode = WeatherCode.get(currentForecast.getInt("weathercode"));
    }

    private void setDailyForecasts(JSONObject data) {
        this.dailyForecasts = new ArrayList<>(16);
        JSONObject dailyForecasts = data.getJSONObject("daily");

        int numForecasts = dailyForecasts.getJSONArray("time").length();
        for (int i = 0; i < numForecasts; i++) {
            this.dailyForecasts.add(new DailyForecast(dailyForecasts, i));
        }
    }

    private void setHourlyForecast(JSONObject data) {
        this.hourlyForecasts = new ArrayList<>(24);
        JSONObject hourlyForecasts = data.getJSONObject("hourly");

        int numForecasts = hourlyForecasts.getJSONArray("time").length();
        for (int i = 0; i < numForecasts; i++) {
            this.hourlyForecasts.add(new HourlyForecast(hourlyForecasts, i));
        }
    }

    /* --------------------------------------------------Getters----------------------------------------------------- */
    public double getTemperature() { return this.temperature; }

    public double getDewPoint() { return this.dewPoint; }

    public double getApparentTemperature() { return this.apparentTemperature; }

    public double getPrecipitation() { return this.precipitation; }

    public double getWindSpeed() { return this.windSpeed; }

    public double getWindDirection() { return this.windDirection; }

    public double getWindGusts() { return this.windGusts; }

    public double getUvIndex() { return this.uvIndex; }

    public double getVisibility() { return this.visibility; }

    public double getFreezingLevel() { return this.freezingLevel; }

    public double getPressure() { return this.pressure; }

    public int getHumidity() { return this.humidity; }

    public int getCloudCover() { return this.cloudCover; }

    public WeatherCode getWeatherCode() { return this.weatherCode; }

    public ArrayList<DailyForecast> getDailyForecasts() { return this.dailyForecasts; }

    public ArrayList<HourlyForecast> getHourlyForecasts() { return this.hourlyForecasts; }

    /* --------------------------------------------------Methods----------------------------------------------------- */
    private JSONObject fetchData() throws IOException, InterruptedException {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast"
                        + "?latitude=%s&longitude=%s"
                        + "&current=temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,"
                        +  "precipitation,weathercode,cloudcover,"
                        +  "windspeed_10m,winddirection_10m,windgusts_10m,"
                        +  "uv_index,is_day,visibility,freezinglevel_height,surface_pressure"
                        + "&daily=temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,"
                        +  "precipitation_sum,rain_sum,snowfall_sum,precipitation_hours,"
                        +  "precipitation_probability_max,precipitation_probability_mean,"
                        +  "weathercode,sunrise,sunset,sunshine_duration,"
                        +  "windspeed_10m_max,windgusts_10m_max,winddirection_10m_dominant,"
                        +  "uv_index_max"
                        + "&hourly=temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,"
                        +  "precipitation_probability,precipitation,snowfall,snow_depth,"
                        +  "weathercode,pressure_msl,cloudcover,cloudcover_low,cloudcover_mid,cloudcover_high,"
                        +  "visibility,windspeed_10m,winddirection_10m,windgusts_10m,"
                        +  "uv_index,is_day,freezinglevel_height"
                        + "&forecast_days=16"
                        + "&timezone=auto",
                this.location.getLatitude(), this.location.getLongitude()
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Return the HTTP response as a JSON object
        return new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
    }

    public boolean isDay() { return this.isDay; }

}

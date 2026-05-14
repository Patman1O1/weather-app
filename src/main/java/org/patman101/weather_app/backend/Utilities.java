package org.patman101.weather_app.backend;

import org.patman101.weather_app.backend.json.weather.objects.Wind;

import java.util.HashMap;
import java.util.Map;

public final class Utilities {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public static final double EPSILON = 1.0E-10;

    public static final Map<String, String> DAY_CONDITION_ICON_PATHS = new HashMap<>(Map.ofEntries(
            Map.entry("CLEAR", "clear_day.png"),
            Map.entry("MOSTLY_CLEAR", "mostly_clear_day.png"),
            Map.entry("PARTLY_CLOUDY", "partly_cloudy_day.png"),
            Map.entry("MOSTLY_CLOUDY", "mostly_cloudy_day.png"),
            Map.entry("CLOUDY", "cloudy.png"),
            Map.entry("WINDY", "windy.png"),
            Map.entry("WIND_AND_RAIN", "rain_showers.png"),
            Map.entry("LIGHT_RAIN_SHOWERS", "drizzle.png"),
            Map.entry("CHANCE_OF_SHOWERS", "rain_showers.png"),
            Map.entry("SCATTERED_SHOWERS", "scattered_showers_day.png"),
            Map.entry("RAIN_SHOWERS", "rain_showers.png"),
            Map.entry("HEAVY_RAIN_SHOWERS", "heavy_rain.png"),
            Map.entry("LIGHT_TO_MODERATE_RAIN", "rain_showers.png"),
            Map.entry("MODERATE_TO_HEAVY_RAIN", "rain_showers.png"),
            Map.entry("RAIN", "rain_showers.png"),
            Map.entry("LIGHT_RAIN", "drizzle.png"),
            Map.entry("HEAVY_RAIN", "heavy_rain.png"),
            Map.entry("RAIN_PERIODICALLY_HEAVY", "heavy_rain.png"),
            Map.entry("LIGHT_SNOW_SHOWERS", "flurries.png"),
            Map.entry("CHANCE_OF_SNOW_SHOWERS", "snow_showers.png"),
            Map.entry("SCATTERED_SNOW_SHOWERS", "scattered_snow_showers_day.png"),
            Map.entry("SNOW_SHOWERS", "snow_showers.png"),
            Map.entry("HEAVY_SNOW_SHOWERS", "heavy_snow.png"),
            Map.entry("LIGHT_TO_MODERATE_SNOW", "snow_showers.png"),
            Map.entry("MODERATE_TO_HEAVY_SNOW", "snow_showers.png"),
            Map.entry("SNOW", "snow_showers.png"),
            Map.entry("LIGHT_SNOW", "flurries.png"),
            Map.entry("HEAVY_SNOW", "heavy_snow.png"),
            Map.entry("SNOWSTORM", "blizzard.png"),
            Map.entry("SNOW_PERIODICALLY_HEAVY", "heavy_snow.png"),
            Map.entry("HEAVY_SNOW_STORM", "blizzard.png"),
            Map.entry("BLOWING_SNOW", "blowing_snow.png"),
            Map.entry("RAIN_AND_SNOW", "mixed_rain_snow.png"),
            Map.entry("HAIL", "hail.png"),
            Map.entry("HAIL_SHOWERS", "hail.png"),
            Map.entry("THUNDERSTORM", "thunderstorms.png"),
            Map.entry("THUNDERSHOWER", "thunderstorms.png"),
            Map.entry("LIGHT_THUNDERSTORM_RAIN", "thunderstorms.png"),
            Map.entry("SCATTERED_THUNDERSTORMS", "scattered_thunderstorms_day.png"),
            Map.entry("HEAVY_THUNDERSTORM", "heavy_thunderstorms.png")
    ));

    public static final Map<String, String> NIGHT_CONDITION_ICON_PATHS = new HashMap<>(Map.ofEntries(
            Map.entry("CLEAR", "clear_night.png"),
            Map.entry("MOSTLY_CLEAR", "mostly_clear_night.png"),
            Map.entry("PARTLY_CLOUDY", "partly_cloudy_night.png"),
            Map.entry("MOSTLY_CLOUDY", "mostly_cloudy_night.png"),
            Map.entry("CLOUDY", "cloudy.png"),
            Map.entry("WINDY", "windy.png"),
            Map.entry("WIND_AND_RAIN", "rain_showers.png"),
            Map.entry("LIGHT_RAIN_SHOWERS", "drizzle.png"),
            Map.entry("CHANCE_OF_SHOWERS", "rain_showers.png"),
            Map.entry("SCATTERED_SHOWERS", "scattered_showers_night.png"),
            Map.entry("RAIN_SHOWERS", "rain_showers.png"),
            Map.entry("HEAVY_RAIN_SHOWERS", "heavy_rain.png"),
            Map.entry("LIGHT_TO_MODERATE_RAIN", "rain_showers.png"),
            Map.entry("MODERATE_TO_HEAVY_RAIN", "rain_showers.png"),
            Map.entry("RAIN", "rain_showers.png"),
            Map.entry("LIGHT_RAIN", "drizzle.png"),
            Map.entry("HEAVY_RAIN", "heavy_rain.png"),
            Map.entry("RAIN_PERIODICALLY_HEAVY", "heavy_rain.png"),
            Map.entry("LIGHT_SNOW_SHOWERS", "flurries.png"),
            Map.entry("CHANCE_OF_SNOW_SHOWERS", "snow_showers.png"),
            Map.entry("SCATTERED_SNOW_SHOWERS", "scattered_snow_showers_night.png"),
            Map.entry("SNOW_SHOWERS", "snow_showers.png"),
            Map.entry("HEAVY_SNOW_SHOWERS", "heavy_snow.png"),
            Map.entry("LIGHT_TO_MODERATE_SNOW", "snow_showers.png"),
            Map.entry("MODERATE_TO_HEAVY_SNOW", "snow_showers.png"),
            Map.entry("SNOW", "snow_showers.png"),
            Map.entry("LIGHT_SNOW", "flurries.png"),
            Map.entry("HEAVY_SNOW", "heavy_snow.png"),
            Map.entry("SNOWSTORM", "blizzard.png"),
            Map.entry("SNOW_PERIODICALLY_HEAVY", "heavy_snow.png"),
            Map.entry("HEAVY_SNOW_STORM", "blizzard.png"),
            Map.entry("BLOWING_SNOW", "blowing_snow.png"),
            Map.entry("RAIN_AND_SNOW", "mixed_rain_snow.png"),
            Map.entry("HAIL", "hail.png"),
            Map.entry("HAIL_SHOWERS", "hail.png"),
            Map.entry("THUNDERSTORM", "thunderstorms.png"),
            Map.entry("THUNDERSHOWER", "thunderstorms.png"),
            Map.entry("LIGHT_THUNDERSTORM_RAIN", "thunderstorms.png"),
            Map.entry("SCATTERED_THUNDERSTORMS", "scattered_thunderstorms_night.png"),
            Map.entry("HEAVY_THUNDERSTORM", "heavy_thunderstorms.png")
    ));

    public static final Map<String, Wind.Direction> DIRECTIONS = new HashMap<>(Map.ofEntries(
            Map.entry("NORTH", Wind.Directions.NORTH),
            Map.entry("NORTH_NORTHEAST", Wind.Directions.NORTH_NORTHEAST),
            Map.entry("NORTHEAST", Wind.Directions.NORTHEAST),
            Map.entry("EAST_NORTHEAST", Wind.Directions.EAST_NORTHEAST),
            Map.entry("EAST", Wind.Directions.EAST),
            Map.entry("EAST_SOUTHEAST", Wind.Directions.EAST_SOUTHEAST),
            Map.entry("SOUTHEAST", Wind.Directions.SOUTHEAST),
            Map.entry("SOUTH_SOUTHEAST", Wind.Directions.SOUTH_SOUTHEAST),
            Map.entry("SOUTH", Wind.Directions.SOUTH),
            Map.entry("SOUTH_SOUTHWEST", Wind.Directions.SOUTH_SOUTHWEST),
            Map.entry("SOUTHWEST", Wind.Directions.SOUTHWEST),
            Map.entry("WEST_SOUTHWEST", Wind.Directions.WEST_SOUTHWEST),
            Map.entry("WEST", Wind.Directions.WEST),
            Map.entry("WEST_NORTHWEST", Wind.Directions.WEST_NORTHWEST),
            Map.entry("NORTHWEST", Wind.Directions.NORTHWEST),
            Map.entry("NORTH_NORTHWEST", Wind.Directions.NORTH_NORTHWEST)
    ));

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private Utilities() {}

    // ── Methods ─────────────────────────────────────────────────────────────────────────────────────────────────
    public static boolean doubleEquals(double lhs, double rhs) { return Math.abs(lhs - rhs) < Utilities.EPSILON; }

    public static <T extends Exception> void printException(Class<T> exceptionClass, String message) {
        System.err.printf("%s thrown with message \"%s\"", exceptionClass, message);
    }
}

package edu.cs342.project2;

public enum WeatherCode {
    /* ------------------------------------------------Enum Constants------------------------------------------------ */
    CLEAR_SKY(0, "Clear Sky"),

    MAINLY_CLEAR(1, "Mainly Clear"),

    PARTLY_CLOUDY(2, "Partly Cloudy"),

    OVERCAST(3, "Overcast"),

    FOG(45, "Fog"),

    RIME_FOG(48, "Rime Fog"),

    LIGHT_DRIZZLE(51, "Light Drizzle"),

    MODERATE_DRIZZLE(53, "Moderate Drizzle"),

    DENSE_DRIZZLE(55, "Dense Drizzle"),

    LIGHT_FREEZING_DRIZZLE(56, "Light Freezing Drizzle"),

    HEAVY_FREEZING_DRIZZLE(57, "Heavy Freezing Drizzle"),

    SLIGHT_RAIN(61, "Slight Rain"),

    MODERATE_RAIN(63, "Moderate Rain"),

    HEAVY_RAIN(65, "Heavy Rain"),

    LIGHT_FREEZING_RAIN(66, "Light Freezing Rain"),

    HEAVY_FREEZING_RAIN(67, "Heavy Freezing Rain"),

    SLIGHT_SNOW(71, "Slight Snow"),

    MODERATE_SNOW(73, "Moderate Snow"),

    HEAVY_SNOW(75, "Heavy Snow"),

    SNOW_GRAINS(77, "Snow Grains"),

    SLIGHT_SHOWERS(80, "Slight Showers"),

    MODERATE_SHOWERS(81, "Moderate Showers"),

    VIOLENT_SHOWERS(82, "Violent Showers"),

    SLIGHT_SNOW_SHOWERS(85, "Slight Snow Showers"),

    HEAVY_SNOW_SHOWERS(86, "Heavy Snow Showers"),

    THUNDERSTORM(95, "Thunderstorm"),

    THUNDERSTORM_SLIGHT_HAIL(96, "Thunderstorm with Hail"),

    THUNDERSTORM_HEAVY_HAIL(99, "Thunderstorm with Heavy Hail");

    /* ---------------------------------------------------Fields----------------------------------------------------- */
    private final int code;

    private final String description;

    /* ------------------------------------------------Constructors-------------------------------------------------- */
    private WeatherCode(int code, String description) { this.code = code; this.description = description; }

    /* --------------------------------------------------Getters----------------------------------------------------- */
    public static WeatherCode get(int code) {
        switch (code) {
            case 0: return CLEAR_SKY;
            case 1: return MAINLY_CLEAR;
            case 2: return PARTLY_CLOUDY;
            case 3: return OVERCAST;
            case 45: return FOG;
            case 48: return RIME_FOG;
            case 51: return LIGHT_DRIZZLE;
            case 53: return MODERATE_DRIZZLE;
            case 55: return DENSE_DRIZZLE;
            case 56: return LIGHT_FREEZING_DRIZZLE;
            case 57: return HEAVY_FREEZING_DRIZZLE;
            case 61: return SLIGHT_RAIN;
            case 63: return MODERATE_RAIN;
            case 65: return HEAVY_RAIN;
            case 66: return LIGHT_FREEZING_RAIN;
            case 67: return HEAVY_FREEZING_RAIN;
            case 71: return SLIGHT_SNOW;
            case 73: return MODERATE_SNOW;
            case 75: return HEAVY_SNOW;
            case 77: return SNOW_GRAINS;
            case 80: return SLIGHT_SHOWERS;
            case 81: return MODERATE_SHOWERS;
            case 82: return VIOLENT_SHOWERS;
            case 85: return SLIGHT_SNOW_SHOWERS;
            case 86: return HEAVY_SNOW_SHOWERS;
            case 95: return THUNDERSTORM;
            case 96: return THUNDERSTORM_SLIGHT_HAIL;
            case 99: return THUNDERSTORM_HEAVY_HAIL;
            default: return null;
        }
    }

    /* --------------------------------------------------Methods----------------------------------------------------- */
    public static WeatherCode getWeatherCode(int code) { return WeatherCode.values()[code]; }

    @Override
    public String toString() { return description; }
}

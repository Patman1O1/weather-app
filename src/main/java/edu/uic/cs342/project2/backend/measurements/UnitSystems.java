package edu.uic.cs342.project2.backend.measurements;

public final class UnitSystems {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public static final UnitSystem METRIC = new UnitSystem(
            "metric",
            new Unit("celsius", "°C"),
            new Unit("hectopascals", "hPa"),
            new Unit("kilometers per hour", "km/h"),
            new Unit("millimeters", "mm"),
            new Unit("kilometers", "km")
    );

    public static final UnitSystem IMPERIAL = new UnitSystem(
            "imperial",
            new Unit("fahrenheit", "°F"),
            new Unit("hectopascals", "hPa"),
            new Unit("miles per hour", "mph"),
            new Unit("inches", "in"),
            new Unit("miles", "mi")
    );
}

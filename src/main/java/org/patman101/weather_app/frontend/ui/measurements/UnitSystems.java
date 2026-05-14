package edu.uic.cs342.project2.frontend.ui.measurements;

public final class UnitSystems {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public static final UnitSystem IMPERIAL = new UnitSystem(
            "imperial",
            new Unit("degrees Fahrenheit", "°F"),
            new Unit("inches", "in"),
            new Unit("miles", "mi"),
            new Unit("miles per hour", "mph")
    );

    public static final UnitSystem METRIC = new UnitSystem(
            "metric",
            new Unit("degrees Celsius", "°C"),
            new Unit("millimeters", "mm"),
            new Unit("kilometers", "km"),
            new Unit("kilometers per hour", "km/h")
    );

    // ── Constructors ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private UnitSystems() {}
}

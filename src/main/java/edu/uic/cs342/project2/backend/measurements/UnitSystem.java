package edu.uic.cs342.project2.backend.measurements;

public class UnitSystem {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private final String name;

    private final Unit temp, pressure, speed, length, distance;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    protected UnitSystem(String name, Unit temp, Unit pressure, Unit speed, Unit length, Unit distance) {
        this.name = name;
        this.temp = temp;
        this.pressure = pressure;
        this.speed = speed;
        this.length = length;
        this.distance = distance;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getName() { return this.name; }

    public Unit getTempUnit() { return this.temp; }

    public Unit getPressureUnit() { return this.pressure; }

    public Unit getSpeedUnit() { return this.speed; }

    public Unit getLengthUnit() { return this.length; }

    public Unit getDistanceUnit() { return this.distance; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return this.name; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UnitSystem)) {
            return false;
        }

        UnitSystem unitSystem = (UnitSystem) object;
        return this.name.equals(unitSystem.name) &&
                this.temp.equals(unitSystem.temp) &&
                this.pressure.equals(unitSystem.pressure) &&
                this.speed.equals(unitSystem.speed) &&
                this.length.equals(unitSystem.length);
    }
}

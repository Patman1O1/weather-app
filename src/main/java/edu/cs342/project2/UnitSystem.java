package edu.cs342.project2;

public enum UnitSystem {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    metric, imperial;

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return this == UnitSystem.metric ? "metric" : "imperial"; }

    public UnitSystem fromString(String systemName) throws NullPointerException, IllegalArgumentException {
        if (systemName == null) {
            throw new NullPointerException("systemName is null");
        }

        if (systemName.equalsIgnoreCase("metric")) {
            return metric;
        }

        if (systemName.equalsIgnoreCase("imperial")) {
            return imperial;
        }

        throw new IllegalArgumentException("Unknown unit system: " + systemName);
    }

}

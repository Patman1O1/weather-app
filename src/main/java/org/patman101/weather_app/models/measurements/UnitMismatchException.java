package org.patman101.weather_app.models.measurements;

public class UnitMismatchException extends RuntimeException {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public UnitMismatchException(String message) { super(message); }
}

package edu.uic.cs342.project2.backend.measurements;

public class Percent extends Measurement {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Percent(double value) throws IllegalArgumentException {
        super(new Unit("percent", "%"));
        this.setValue(value);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public void setValue(double value) throws IllegalArgumentException {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("value must be between 0 and 100 (inclusive)");
        }
        super.setValue(value);
    }
}

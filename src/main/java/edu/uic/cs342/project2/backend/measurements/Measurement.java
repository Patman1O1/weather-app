package edu.uic.cs342.project2.backend.measurements;

public class Measurement extends Number implements Comparable<Measurement> {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final double EPSILON = 1.0e-9;

    private double value;

    private Unit unit;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Measurement(Unit unit) throws NullPointerException { this.setValue(0.0); this.setUnit(unit); }

    public Measurement(double value, Unit unit) throws NullPointerException { this.setValue(value); this.setUnit(unit); }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setValue(double value) { this.value = value; }

    private void setUnit(Unit unit) throws NullPointerException {
        if (unit == null) {
            throw new NullPointerException();
        }
        this.unit = unit;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Unit getUnit() { return this.unit; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private static boolean doubleEquals(double lhs, double rhs) { return Math.abs(lhs - rhs) < Measurement.EPSILON; }

    @Override
    public String toString() { return String.format("%.2f", this.value) + " " + this.unit.getSymbol(); }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Measurement)) {
            return false;
        }

        Measurement other = (Measurement) object;
        return Measurement.doubleEquals(this.value, other.value) && this.unit.equals(other.unit);
    }

    @Override
    public int compareTo(Measurement other) throws NullPointerException, IllegalStateException {
        if (other == null) {
            throw new NullPointerException("other is null");
        }

        if (!this.unit.equals(other.unit)) {
            throw new IllegalStateException("cannot compare two measurements with different units");
        }

        if (Measurement.doubleEquals(this.value, other.value)) {
            return 0;
        }

        if (this.value < other.value) {
            return -1;
        }

        return 1;
    }

    @Override
    public int intValue() { return (int) this.value; }

    @Override
    public long longValue() { return (long) this.value; }

    @Override
    public float floatValue() { return (float) this.value; }

    @Override
    public double doubleValue() { return this.value; }
}

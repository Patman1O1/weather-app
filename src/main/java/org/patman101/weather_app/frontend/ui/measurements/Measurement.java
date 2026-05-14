package edu.uic.cs342.project2.frontend.ui.measurements;

public class Measurement implements Comparable<Measurement> {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Number number;

    private Unit unit;

    // ── Constructors ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public Measurement(Number number, Unit unit) throws NullPointerException {
        this.setNumber(number);
        this.setUnit(unit);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setNumber(Number number) {
        if (number == null) {
            throw new NullPointerException("number is null");
        }
        this.number = number;
    }

    public void setUnit(Unit unit) throws NullPointerException {
        if (unit == null) {
            throw new NullPointerException("unit is null");
        }
        this.unit = unit;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Number getNumber() { return this.number; }

    public Unit getUnit() { return this.unit; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public int compareTo(Measurement other) throws NullPointerException, UnitMismatchException {
        if (other == null) {
            throw new NullPointerException("other is null");
        }

        if (!this.unit.equals(other.unit)) {
            throw new UnitMismatchException(String.format("Cannot compare two measurements with different units: comparing %s to %s", this.unit, other.unit));
        }

        return Double.compare(this.number.doubleValue(), other.number.doubleValue());
    }

    @Override
    public String toString() { return String.format("%d %s", this.number.intValue(), this.unit); }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Measurement)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Measurement other = (Measurement) object;
        return this.number.equals(other.number) && this.unit.equals(other.unit);
    }
}

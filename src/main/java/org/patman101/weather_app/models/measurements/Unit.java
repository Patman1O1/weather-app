package org.patman101.weather_app.models.measurements;

public class Unit {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private String name, symbol;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Unit(String name, String symbol) throws NullPointerException {
        this.setName(name);
        this.setSymbol(symbol);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setName(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        this.name = name;
    }

    public void setSymbol(String symbol) throws NullPointerException {
        if (symbol == null) {
            throw new NullPointerException("symbol is null");
        }
        this.symbol = symbol;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getName() { return this.name; }

    public String getSymbol() { return this.symbol; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return this.symbol; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Unit other)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        return this.name.equals(other.name) && this.symbol.equals(other.symbol);
    }
}

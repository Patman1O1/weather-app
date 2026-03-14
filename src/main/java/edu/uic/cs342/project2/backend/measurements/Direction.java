package edu.uic.cs342.project2.backend.measurements;

public class Direction extends Measurement {
    // ── CardinalValue ────────────────────────────────────────────────────────────────────────────────────────────────
    public static class CardinalValue {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private String name, symbol;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        private CardinalValue(String name, String symbol) throws NullPointerException {
            this.setName(name);
            this.setSymbol(symbol);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        private void setName(String name) throws NullPointerException {
            if (name == null) {
                throw new NullPointerException("name is null");
            }
            this.name = name;
        }

        private void setSymbol(String symbol) throws NullPointerException {
            if (symbol == null) {
                throw new NullPointerException("symbol is null");
            }
            this.symbol = symbol;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public String getName() { return this.name; }

        public String getSymbol() { return this.symbol; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public String toString() { return this.name; }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof CardinalValue)) {
                return false;
            }
            CardinalValue other = (CardinalValue) object;
            return this.name.equals(other.name) && this.symbol.equals(other.symbol);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final Unit DEGREES = new Unit("degrees", "°");

    private static final CardinalValue[] CARDINAL_VALUES = {
            new CardinalValue("North", "N"),
            new CardinalValue("North Northeast", "NNE"),
            new CardinalValue("Northeast", "NE"),
            new CardinalValue("East Northeast", "ENE"),
            new CardinalValue("East", "E"),
            new CardinalValue("East Southeast", "ESE"),
            new CardinalValue("Southeast", "SE"),
            new CardinalValue("South Southeast", "SSE"),
            new CardinalValue("South", "S"),
            new CardinalValue("South Southwest", "SSW"),
            new CardinalValue("Southwest", "SW"),
            new CardinalValue("West Southwest", "WSW"),
            new CardinalValue("West", "W"),
            new CardinalValue("West Northwest", "WNW"),
            new CardinalValue("Northwest", "NW"),
            new CardinalValue("North Northwest", "NNW")
    };

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Direction(double degrees) throws NullPointerException { super(degrees, Direction.DEGREES); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    //private static double normalize(double value) { return ((value % 360) + 360) % 360; }

    @Override
    public String toString() { return String.format("%.2f%s", super.doubleValue(), Direction.DEGREES.getSymbol()); }

    public CardinalValue cardinalValue() {
        return CARDINAL_VALUES[(int) Math.floor((super.doubleValue() + 11.25) / 22.5) % 16];
    }
}

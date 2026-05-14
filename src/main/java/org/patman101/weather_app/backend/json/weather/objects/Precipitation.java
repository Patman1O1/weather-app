package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.patman101.weather_app.backend.Utilities;
import org.patman101.weather_app.backend.json.weather.deserializers.PrecipitationDeserializer;
import org.patman101.weather_app.frontend.ui.measurements.Measurement;
import org.patman101.weather_app.frontend.ui.measurements.Unit;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = PrecipitationDeserializer.class)
public class Precipitation {
    // ── Probability ──────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Probability {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private String type;

        public double percent;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Probability(double percent, String type) throws NullPointerException {
            this.percent = percent;
            this.setType(type);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setType(String type) throws NullPointerException {
            if (type == null) {
                throw new NullPointerException("type is null");
            }
            this.type = type;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public String getType() { return this.type; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Probability)) {
                return false;
            }

            if (this == object) {
                return true;
            }

            Probability other = (Probability) object;
            return Utilities.doubleEquals(this.percent, other.percent) && this.type.equals(other.type);
        }
    }

    // ── Qpf ──────────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Qpf extends Measurement {

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Qpf(Number number, Unit unit) throws NullPointerException {
           super(number, unit);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Probability probability;

    private Qpf qpf;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Precipitation(Probability probability, Qpf qpf) throws NullPointerException {
        this.setProbability(probability);
        this.setQpf(qpf);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setProbability(Probability probability) throws NullPointerException {
        if (probability == null) {
            throw new NullPointerException("probability is null");
        }
        this.probability = probability;
    }

    public void setQpf(Qpf qpf) throws NullPointerException {
        if (qpf == null) {
            throw new NullPointerException("qpf is null");
        }
        this.qpf = qpf;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Probability getProbability() { return this.probability; }

    public Qpf getQpf() { return this.qpf; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Precipitation)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Precipitation other = (Precipitation) object;
        return this.probability.equals(other.probability) && this.qpf.equals(other.qpf);
    }
}

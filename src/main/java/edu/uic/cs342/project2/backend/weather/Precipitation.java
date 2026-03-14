package edu.uic.cs342.project2.backend.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.uic.cs342.project2.backend.deserializers.PrecipitationDeserializer;
import edu.uic.cs342.project2.backend.measurements.Measurement;
import edu.uic.cs342.project2.backend.measurements.Percent;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = PrecipitationDeserializer.class)
public class Precipitation {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private String type;

    private Percent probability;

    private Measurement qpf;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Precipitation(String type, Percent probability, Measurement qpf) throws NullPointerException {
        this.setType(type);
        this.setProbability(probability);
        this.setQpf(qpf);
    }
    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setType(String type) throws NullPointerException {
        if (type == null) {
            throw new NullPointerException("type is null");
        }
        this.type = type;
    }

    public void setProbability(Percent probability) throws NullPointerException {
        if (probability == null) {
            throw new NullPointerException("probability is null");
        }
        this.probability = probability;
    }

    public void setQpf(Measurement qpf) throws NullPointerException {
        if (qpf == null) {
            throw new NullPointerException("qpf is null");
        }
        this.qpf = qpf;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getType() { return this.type; }

    public Percent getProbability() { return this.probability; }

    public Measurement getQpf() { return this.qpf; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
                "Type: %s\n" +
                "Probability: %.2f%%\n" +
                "QPF: %s",
                this.type,
                this.probability.doubleValue(),
                this.qpf.toString());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Precipitation)) {
            return false;
        }
        Precipitation other = (Precipitation) object;
        return this.type.equals(other.type) && this.probability.equals(other.probability) && this.qpf.equals(other.qpf);
    }
}

package edu.uic.cs342.project2.backend.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.uic.cs342.project2.backend.deserializers.WeatherConditionDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = WeatherConditionDeserializer.class)
public class WeatherCondition {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private String type, description;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public WeatherCondition(String type, String description) throws NullPointerException{
        this.setType(type);
        this.setDescription(description);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setType(String type) throws NullPointerException {
        if (type == null) {
            throw new NullPointerException("type is null");
        }
        this.type = type;
    }

    public void setDescription(String description) throws NullPointerException {
        if (description == null) {
            throw new NullPointerException("description is null");
        }
        this.description = description;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getType() { return this.type; }

    public String getDescription() { return this.description; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("Condition Type: %s\nDescription: %s", this.type, this.description);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WeatherCondition)) {
            return false;
        }

        WeatherCondition other = (WeatherCondition) object;
        return this.type.equals(other.type) && this.description.equals(other.description);
    }
}

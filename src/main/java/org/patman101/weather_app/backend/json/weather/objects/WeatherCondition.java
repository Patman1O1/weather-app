package edu.uic.cs342.project2.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.uic.cs342.project2.backend.json.weather.deserializers.WeatherConditionDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = WeatherConditionDeserializer.class)
public class WeatherCondition {
    // ── Fields ─────────────────────────────────────────────────────────────────────────────────────────────────
    private String type, description, iconPath;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public WeatherCondition(String type, String description, String iconPath) throws NullPointerException {
        this.setType(type);
        this.setDescription(description);
        this.setIconPath(iconPath);
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

    public void setIconPath(String iconPath) throws NullPointerException {
        if (iconPath == null) {
            throw new NullPointerException("iconPath is null");
        }
        this.iconPath = iconPath;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getType() { return this.type; }

    public String getDescription() { return this.description; }

    public String getIconPath() { return this.iconPath; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WeatherCondition)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        WeatherCondition other = (WeatherCondition) object;
        return this.type.equals(other.type) &&
                this.description.equals(other.description) &&
                this.iconPath.equals(other.iconPath);
    }
}

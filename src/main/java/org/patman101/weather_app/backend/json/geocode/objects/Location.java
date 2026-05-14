package edu.uic.cs342.project2.backend.json.geocode.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.uic.cs342.project2.backend.Utilities;
import edu.uic.cs342.project2.backend.json.geocode.deserializers.LocationDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = LocationDeserializer.class)
public class Location {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private String formattedAddress;

    private AddressComponents addressComponents;

    public double latitude, longitude;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Location() {
        this.formattedAddress = null;
        this.addressComponents = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public Location(String formattedAddress,
                    AddressComponents addressComponents,
                    double latitude,
                    double longitude) throws NullPointerException {
        this.setFormattedAddress(formattedAddress);
        this.setAddressComponents(addressComponents);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setFormattedAddress(String formattedAddress) throws NullPointerException {
        if (formattedAddress == null) {
            throw new NullPointerException("formattedAddress is null");
        }
        this.formattedAddress = formattedAddress;
    }

    public void setAddressComponents(AddressComponents addressComponents) throws NullPointerException {
        if (addressComponents == null) {
            throw new NullPointerException("addressComponents is null");
        }
        this.addressComponents = addressComponents;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getFormattedAddress() { return this.formattedAddress; }

    public AddressComponents getAddressComponents() { return this.addressComponents; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Location)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        Location other = (Location) object;
        return this.formattedAddress.equals(other.formattedAddress) &&
                this.addressComponents.equals(other.addressComponents) &&
                Utilities.doubleEquals(this.latitude, other.latitude) &&
                Utilities.doubleEquals(this.longitude, other.longitude);
    }
}

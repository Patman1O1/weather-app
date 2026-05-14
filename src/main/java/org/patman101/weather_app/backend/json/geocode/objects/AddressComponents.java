package org.patman101.weather_app.backend.json.geocode.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.patman101.weather_app.backend.json.geocode.deserializers.AddressComponentsDeserializer;

import java.util.ArrayList;
import java.util.Iterator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = AddressComponentsDeserializer.class)
public class AddressComponents implements Iterable<AddressComponents.AddressComponent> {
    // ── Address Component ────────────────────────────────────────────────────────────────────────────────────────────
    public static class AddressComponent {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private String longName, shortName;

        private ArrayList<String> types;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public AddressComponent(String longName, String shortName, ArrayList<String> types)
                throws NullPointerException {
            this.setLongName(longName);
            this.setShortName(shortName);
            this.setTypes(types);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setLongName(String longName) throws NullPointerException {
            if (longName == null) {
                throw new NullPointerException("longName is null");
            }
            this.longName = longName;
        }

        public void setShortName(String shortName) throws NullPointerException {
            if (shortName == null) {
                throw new NullPointerException("shortName is null");
            }
            this.shortName = shortName;
        }

        public void setTypes(ArrayList<String> types) throws NullPointerException {
            if (types == null) {
                throw new NullPointerException("types is null");
            }
            this.types = types;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public String getLongName() { return this.longName; }

        public String getShortName() { return this.shortName; }

        public ArrayList<String> getTypes() { return this.types; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof AddressComponent)) {
                return false;
            }

            if (object == this) {
                return true;
            }

            AddressComponent other = (AddressComponent) object;
            return this.longName.equals(other.longName) && this.shortName.equals(other.shortName);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private ArrayList<AddressComponent> addressComponents;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public AddressComponents(ArrayList<AddressComponent> addressComponents) throws NullPointerException {
        this.setAddressComponents(addressComponents);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setAddressComponents(ArrayList<AddressComponent> addressComponents) throws NullPointerException {
        if (addressComponents == null) {
            throw new NullPointerException("addressComponents is null");
        }
        this.addressComponents = addressComponents;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public ArrayList<AddressComponent> getAddressComponents() { return this.addressComponents; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AddressComponents)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        AddressComponents other = (AddressComponents) object;
        return this.addressComponents.equals(other.addressComponents);
    }

    @Override
    public Iterator<AddressComponent> iterator() { return this.addressComponents.iterator(); }
}

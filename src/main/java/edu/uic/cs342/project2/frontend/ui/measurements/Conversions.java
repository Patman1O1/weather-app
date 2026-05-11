package edu.uic.cs342.project2.frontend.ui.measurements;

public final class Conversions {

    private Conversions() {}

    public static double celsiusToFahrenheit(double degreesCelsius) { return degreesCelsius * 1.8 + 32.0; }

    public static double fahrenheitToCelsius(double degreesFahrenheit) { return (degreesFahrenheit - 32.0) / 1.8; }

    public static double millimetersToInches(double millimeters) { return millimeters / 25.4; }

    public static double inchesToMillimeters(double inches) { return inches * 25.4; }

    public static double kilometersToMiles(double kilometers) { return kilometers * 1.609344; }

    public static double milesToKilometers(double miles) { return miles / 1.609344; }
}

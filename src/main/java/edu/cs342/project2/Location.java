package edu.cs342.project2;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.*;

public class Location {
    /* ---------------------------------------------------Fields----------------------------------------------------- */
    private static final String GOOGLE_API_KEY = "AIzaSyAXOFEgdWxFrqUpztN3vBl1gI-T1mpXVGk";

    private double latitude, longitude, accuracy;

    /* -------------------------------------------------Constructors------------------------------------------------- */
    public Location() throws IOException { this.findLocation(); }

    /* ---------------------------------------------------Getters---------------------------------------------------- */
    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public double getAccuracy() { return accuracy; }

    /* ---------------------------------------------------Methods---------------------------------------------------- */
    private static String httpPost(String body) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(
                "https://www.googleapis.com/geolocation/v1/geolocate?key=" + GOOGLE_API_KEY
        ).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "MyApp/1.0");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        return sb.toString();
    }

    private void findLocation() throws IOException {
        JSONArray wifiList = new JSONArray();
        String os = System.getProperty("os.name").toLowerCase();
        Process process;

        if (os.contains("win")) {
            process = Runtime.getRuntime().exec("netsh wlan show networks mode=bssid");
        } else if (os.contains("mac")) {
            process = Runtime.getRuntime().exec(
                    "/System/Library/PrivateFrameworks/Apple80211.framework" +
                            "/Versions/Current/Resources/airport -s");
        } else {
            // Linux
            process = Runtime.getRuntime().exec("nmcli -t -f BSSID,SIGNAL dev wifi list");
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        String currentBssid = null;
        int currentSignal = -65; // default

        while ((line = reader.readLine()) != null) {
            if (os.contains("win")) {
                if (line.contains("BSSID")) {
                    currentBssid = line.split(":")[1].trim() + ":"
                            + line.split(":")[2].trim() + ":"
                            + line.split(":")[3].trim() + ":"
                            + line.split(":")[4].trim() + ":"
                            + line.split(":")[5].trim() + ":"
                            + line.split(":")[6].trim();
                } else if (line.contains("Signal")) {
                    currentSignal = Integer.parseInt(line.split(":")[1].trim()
                            .replace("%", ""));
                    // Convert Windows % signal to dBm (approximate)
                    currentSignal = (currentSignal / 2) - 100;

                    if (currentBssid != null) {
                        JSONObject ap = new JSONObject();
                        ap.put("macAddress", currentBssid);
                        ap.put("signalStrength", currentSignal);
                        wifiList.put(ap);
                        currentBssid = null;
                    }
                }
            } else if (os.contains("mac") && !line.startsWith("SSID")) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 3) {
                    JSONObject ap = new JSONObject();
                    ap.put("macAddress", parts[1]);
                    ap.put("signalStrength", Integer.parseInt(parts[2]));
                    wifiList.put(ap);
                }
            } else {
                // Linux nmcli output: BSSID:SIGNAL
                String[] parts = line.split(":");
                if (parts.length >= 7) {
                    JSONObject ap = new JSONObject();
                    String mac = parts[0]+":"+parts[1]+":"+parts[2]+":"
                            +parts[3]+":"+parts[4]+":"+parts[5];
                    ap.put("macAddress", mac);
                    ap.put("signalStrength", Integer.parseInt(parts[6]));
                    wifiList.put(ap);
                }
            }
        }

        if (wifiList.isEmpty()) {
            throw new IOException("No Wi-Fi networks found");
        }

        // Build request for Mozilla Location Services (free, no key needed)
        JSONObject body = new JSONObject();
        body.put("wifiAccessPoints", wifiList);

        String response = httpPost(
                body.toString()
        );

        JSONObject json = new JSONObject(response);
        JSONObject location = json.getJSONObject("location");

        // Set fields
        this.latitude = location.getDouble("lat");
        this.longitude = location.getDouble("lng");
        this.accuracy = json.getDouble("accuracy");
    }

    @Override
    public String toString() {
        return "Latitude: " + this.latitude + ", Longitude: " + this.longitude;
    }
}
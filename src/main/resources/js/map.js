let map;
let markers = [];

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 40.7128, lng: -74.0060 }, // New York
        zoom: 12
    });

    // Click listener - sends data back to Java
    map.addListener("click", function(event) {
        const lat = event.latLng.lat();
        const lng = event.latLng.lng();

        addMarker(lat, lng);
        // Call Java method via JSBridge
        if (window.javaApp) {
            window.javaApp.onMapClick(lat, lng);
        }
    });
}

// Called from Java to add a marker
function addMarker(lat, lng, title) {
    const marker = new google.maps.Marker({
    position: { lat: lat, lng: lng }, map: map, title: title || "" });
    markers.push(marker);
}

// Called from Java to move/center the map
function panTo(lat, lng) { map.panTo({ lat: lat, lng: lng }); }

// Called from Java to clear all markers
function clearMarkers() {
    markers.forEach(m => m.setMap(null));
    markers = [];
}
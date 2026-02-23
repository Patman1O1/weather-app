let map;
let markers = [];

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 40.7128, lng: -74.0060 },
        zoom: 12
    });

    map.addListener("click", function(event) {
        const lat = event.latLng.lat();
        const lng = event.latLng.lng();

        addMarker(lat, lng);

        if (window.weatherApp) {
            window.weatherApp.onMapClick(lat, lng);
        }
    });

    map.addListener("center_changed", function() {
        const center = map.getCenter();
        const lat = center.lat();
        const lng = center.lng();
        if (window.weatherApp) {
            window.weatherApp.onMapMove(lat, lng);
        }
    });

    map.addListener("idle", function() {
        const center = map.getCenter();
        const lat = center.lat();
        const lng = center.lng();
        if (window.weatherApp) {
            window.weatherApp.onMapIdle(lat, lng);
        }
    });
}

function addMarker(lat, lng, title) {
    const marker = new google.maps.Marker({
        position: { lat: lat, lng: lng },
        map: map,
        title: title || ""
    });
    markers.push(marker);
}

function panTo(lat, lng) {
    map.panTo({ lat: lat, lng: lng });
}

function clearMarkers() {
    markers.forEach(m => m.setMap(null));
    markers = [];
}
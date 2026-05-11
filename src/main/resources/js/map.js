// ── CONFIG ────────────────────────────────────────────────────────────────────
let OWM_KEY = '53377062fe082b9779447735e252d756'; // API key — like a String field
let units = 'metric';
let isDark = true;
let activeLayer = 'temp_new';
let currentTileLayer = null;

// document.getElementById() is like calling scene.lookup("#weather-popup") in JavaFX.
// It finds an HTML element by its 'id' attribute and returns a reference to it.
const popup = document.getElementById('weather-popup');

// ── LAYERS ────────────────────────────────────────────────────────────────────
// An Array of plain JavaScript objects — like an ObservableList<WeatherLayer>
// Each object {} is a map of key:value pairs
const LAYERS = [
    { id: 'temp_new',          label: 'Temperature',   icon: '🌡',  color: '#f24822',
        gradient: 'linear-gradient(90deg,#0d99ff,#1bc47d,#f7b955,#f24822,#9747ff)',
        legend: ['-40°','-20°','0°','20°','40°'] },
    { id: 'precipitation_new', label: 'Precipitation', icon: '🌧',  color: '#0d99ff',
        gradient: 'linear-gradient(90deg,#2c2c2c,#0d99ff,#0072cc,#004d99)',
        legend: ['None','Light','Mod','Heavy','Ext'] },
    { id: 'wind_new',          label: 'Wind',          icon: '🌬',  color: '#9747ff',
        gradient: 'linear-gradient(90deg,#e5e5e5,#9747ff,#6929cc,#3d1a99)',
        legend: ['0','10','20','30','50+'] },
    { id: 'clouds_new',        label: 'Cloud',         icon: '☁',  color: '#8c8c8c',
        gradient: 'linear-gradient(90deg,#1e1e1e,#4d4d4d,#8c8c8c,#e5e5e5)',
        legend: ['0%','25%','50%','75%','100%'] },
    { id: 'pressure_new',      label: 'Pressure',      icon: '◎',  color: '#1bc47d',
        gradient: 'linear-gradient(90deg,#f24822,#f7b955,#1bc47d,#0d99ff,#9747ff)',
        legend: ['940','970','1000','1030','1060'] },
];

// ── MAP ───────────────────────────────────────────────────────────────────────
// LocalStorage is a browser key-value store (like Java Preferences API).
// This wraps its setItem() method to silently ignore QuotaExceededErrors —
// equivalent to surrounding a Java Preferences.put() call with try/catch.
const _origSetItem = Storage.prototype.setItem;
Storage.prototype.setItem = function(k, v) { try { _origSetItem.call(this, k, v); } catch(e) {} };

// L.map() creates a Leaflet map — a third-party mapping library.
// 'map' is the HTML element id that the map will be rendered inside.
const map = L.map('map', { center: [30, 10], zoom: 3, zoomControl: false, attributionControl: true });

// L.tileLayer() creates a map tile overlay — tiles are 256×256px map image
// squares fetched from a server based on zoom/x/y coordinates.
// These four are like toggling between two stylesheet themes for the base map.
// CARTO provides dark and light variants; labels are a separate layer so they
// can sit on top of the weather overlay without being obscured.
const tileDark  = L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_nolabels/{z}/{x}/{y}{r}.png',
    { attribution: '© OpenStreetMap © CARTO', subdomains: 'abcd', maxZoom: 20 });
const tileLight = L.tileLayer('https://{s}.basemaps.cartocdn.com/light_nolabels/{z}/{x}/{y}{r}.png',
    { attribution: '© OpenStreetMap © CARTO', subdomains: 'abcd', maxZoom: 20 });
const tileDarkLabels  = L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_only_labels/{z}/{x}/{y}{r}.png',
    { subdomains: 'abcd', maxZoom: 20, zIndex: 10 }); // zIndex like Z-order in JavaFX scene graph
const tileLightLabels = L.tileLayer('https://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}{r}.png',
    { subdomains: 'abcd', maxZoom: 20, zIndex: 10 });

tileDark.addTo(map);       // addTo() adds the layer to the map — like getChildren().add() on a Pane
tileDarkLabels.addTo(map); // Labels layer is added on top so they render over the weather overlay

// ── LAYER BUTTONS ─────────────────────────────────────────────────────────────
// Dynamically builds a row of toggle buttons — similar to adding ToggleButton
// nodes to an HBox in JavaFX from a list of items.
// innerHTML is like clearing and re-adding children to a container Pane.
function buildLayerBtns() {
    const container = document.getElementById('layer-btns');
    container.innerHTML = ''; // Clears all existing children — like container.getChildren().clear()
    LAYERS.forEach(l => {
        // createElement() creates a new HTML element not yet added to the page —
        // like instantiating a new Button() before adding it to the scene.
        const btn = document.createElement('button');

        // className sets the CSS class on the element — like btn.getStyleClass().add("layer-btn").
        // The ternary (condition ? a : b) works the same as in Java.
        btn.className = 'layer-btn' + (l.id === activeLayer ? ' active' : '');

        // innerHTML sets the inner HTML markup of the element — like adding child nodes.
        // Template literals use backticks (`) and ${} for interpolation — like String.format() in Java.
        btn.innerHTML = `<span class="dot" style="background:${l.id === activeLayer ? 'rgba(255,255,255,0.7)' : l.color}"></span>${l.label}`;

        // btn.onclick is an event handler — like btn.setOnAction(e -> switchLayer(l.id)) in JavaFX.
        // Arrow functions (x => expr) are like short lambda expressions.
        btn.onclick = () => switchLayer(l.id);

        container.appendChild(btn); // Adds the button to the DOM — like container.getChildren().add(btn)
    });
}
buildLayerBtns(); // Called immediately — like initialize() in a JavaFX Controller

// Updates button visual state to reflect the newly active layer.
// querySelectorAll returns a NodeList of all matching elements —
// like looking up all nodes with a given style class in JavaFX's scene graph.
function switchLayer(layerId) {
    activeLayer = layerId;
    document.querySelectorAll('.layer-btn').forEach((b, i) => {
        // classList.toggle(class, bool) adds or removes a CSS class based on the boolean —
        // like toggling a PseudoClass in JavaFX (e.g. PseudoClass.getPseudoClass("active")).
        b.classList.toggle('active', LAYERS[i].id === layerId);
        // .style.background sets an inline CSS style directly on the element —
        // like calling btn.setStyle("-fx-background-color: ...") in JavaFX.
        b.querySelector('.dot').style.background = LAYERS[i].id === layerId ? 'rgba(255,255,255,0.7)' : LAYERS[i].color;
    });
    updateOWMTile();
    updateLegend();
}

// Swaps the weather overlay tile layer on the map.
// removeLayer() is like getChildren().remove() in JavaFX —
// the old overlay is removed before the new one is added to avoid stacking.
function updateOWMTile() {
    if (currentTileLayer) { map.removeLayer(currentTileLayer); currentTileLayer = null; }
    if (!OWM_KEY) return;
    currentTileLayer = L.tileLayer(
        `https://tile.openweathermap.org/map/${activeLayer}/{z}/{x}/{y}.png?appid=${OWM_KEY}`,
        { opacity: 0.8, attribution: '© OpenWeatherMap', maxZoom: 18 }
    );
    currentTileLayer.addTo(map);
}

// Updates the legend bar at the bottom of the map.
// Array.find() works like Java's Stream.filter(...).findFirst().orElse(null).
function updateLegend() {
    const layer = LAYERS.find(l => l.id === activeLayer);
    if (!layer) return;
    document.getElementById('legend-title').textContent = layer.label; // .textContent sets plain text — like Label.setText()
    document.getElementById('legend-bar').style.background = layer.gradient;
    // .map() transforms each item — like Java Stream.map().collect(joining()).
    // .join() concatenates strings with a separator — like String.join() in Java.
    document.getElementById('legend-labels').innerHTML = layer.legend.map(l => `<span>${l}</span>`).join('');
}
updateLegend();
updateOWMTile();

// ── THEME ─────────────────────────────────────────────────────────────────────
// Toggles between dark and light mode — like swapping stylesheets in JavaFX
// with scene.getStylesheets().setAll("dark.css") vs "light.css".
// setAttribute sets an HTML attribute on the element — here it drives CSS rules
// written as [data-theme="dark"] { ... } selectors in the stylesheet.
function toggleTheme() {
    isDark = !isDark;
    document.body.setAttribute('data-theme', isDark ? 'dark' : 'light');
    document.getElementById('theme-btn').textContent = isDark ? '☾' : '☀';
    if (isDark) {
        map.removeLayer(tileLight); map.removeLayer(tileLightLabels);
        tileDark.addTo(map); tileDarkLabels.addTo(map);
    } else {
        map.removeLayer(tileDark); map.removeLayer(tileDarkLabels);
        tileLight.addTo(map); tileLightLabels.addTo(map);
    }
}

// ── UNITS ─────────────────────────────────────────────────────────────────────
// Sets the active unit system (metric/imperial).
// dataset.unit reads the HTML data-unit="..." attribute —
// like reading custom properties from a JavaFX Node's properties map.
function setUnit(u) {
    units = u;
    document.querySelectorAll('.unit-btn').forEach(b => b.classList.toggle('active', b.dataset.unit === u));
}

// ── GEOLOCATION ───────────────────────────────────────────────────────────────
// navigator.geolocation is a browser API — like requesting location permission
// on Android. getCurrentPosition() is asynchronous: it accepts two callbacks,
// one for success and one for failure — similar to a Task<GeolocationResult>
// with setOnSucceeded() and setOnFailed() handlers in JavaFX.
function goToLocation() {
    const btn = document.getElementById('loc-btn');
    btn.style.opacity = '0.4'; // Dims the button while the request is in flight — like setDisable(true)
    navigator.geolocation.getCurrentPosition(pos => {
        map.setView([pos.coords.latitude, pos.coords.longitude], 9);
        btn.style.opacity = '1';
        fetchWeather(pos.coords.latitude, pos.coords.longitude);
    }, () => { btn.style.opacity = '1'; }); // Error callback — restore the button if location is denied
}

// ── SEARCH ────────────────────────────────────────────────────────────────────
// These three lines grab references to DOM elements by id —
// like @FXML field injection in a JavaFX Controller class.
const searchInput    = document.getElementById('search-input');
const searchClear    = document.getElementById('search-clear');
const searchDropdown = document.getElementById('search-dropdown');
let searchDebounce = null;  // Holds the ID of a pending delayed call — used to cancel and restart the timer
let highlightIndex = -1;    // Tracks which dropdown item is keyboard-highlighted, like a selectedIndex
let currentResults = [];    // The last set of search results — like an ObservableList backing a ListView

// addEventListener() attaches an event listener — like searchInput.setOnKeyReleased() in JavaFX.
// 'input' fires every time the text field's value changes.
searchInput.addEventListener('input', () => {
    const q = searchInput.value.trim(); // .value is the current text — like TextField.getText()
    // classList.toggle(class, bool) — adds the class when bool is true, removes it when false.
    searchClear.classList.toggle('visible', q.length > 0);
    clearTimeout(searchDebounce); // Cancel the previous pending call — like cancelling a scheduled PauseTransition
    if (q.length < 2) {
        closeDropdown();
        return;
    }
    // setTimeout() schedules a function to run after a delay (ms) — like a PauseTransition.
    // This debounce pattern waits for the user to pause typing before firing the API call.
    searchDebounce = setTimeout(() => geocodeSearch(q), 280);
});

// Keyboard navigation for the dropdown — equivalent to handling KEY_PRESSED on a ListView.
// ArrowDown/Up move the highlight; Enter selects; Escape closes — all standard listbox behaviour.
searchInput.addEventListener('keydown', keyboardEvent => {
    const items = searchDropdown.querySelectorAll('.dd-item');
    if (keyboardEvent.key === 'ArrowDown') {
        keyboardEvent.preventDefault();
        highlightIndex = Math.min(highlightIndex + 1, items.length - 1); updateHighlight(items);
    } else if (keyboardEvent.key === 'ArrowUp') {
        keyboardEvent.preventDefault();
        highlightIndex = Math.max(highlightIndex - 1, -1);
        updateHighlight(items);
    } else if (keyboardEvent.key === 'Enter') {
        const result = highlightIndex >= 0 ? currentResults[highlightIndex] : currentResults[0];
        if (result) {
            selectResult(result);
        }
    } else if (keyboardEvent.key === 'Escape') {
        closeDropdown();
        searchInput.blur();
    } // .blur() removes keyboard focus — like node.setFocused(false)
});

// Applies visual highlight to the keyboard-selected dropdown item.
// scrollIntoView() ensures the highlighted row is visible if the list is scrollable —
// like scrolling a ListView to reveal the selected item.
function updateHighlight(items) {
    items.forEach((element, index) => element.classList.toggle('highlighted', index === highlightIndex));
    if (highlightIndex >= 0 && items[highlightIndex]) {
        items[highlightIndex].scrollIntoView({ block: 'nearest' });
    }
}

// Clears the text field and closes the dropdown when the × button is clicked.
searchClear.addEventListener('click', () => {
    searchInput.value = '';
    searchClear.classList.remove('visible');
    closeDropdown();
    searchInput.focus(); // .focus() requests keyboard focus — like node.requestFocus() in JavaFX
});

// Closes the dropdown when the user clicks anywhere outside the search wrapper —
// like a ChangeListener on stage.focusedProperty() in JavaFX to detect focus loss.
// e.target is the element that was clicked; .contains() checks if it's inside the wrapper.
document.addEventListener('click', e => { if (!document.getElementById('search-wrapper').contains(e.target)) closeDropdown(); });

// Collapses and clears the dropdown list.
function closeDropdown() {
    searchDropdown.classList.remove('open');
    searchDropdown.innerHTML = '';
    highlightIndex = -1;
    currentResults = [];
}

// Calls the OpenWeatherMap geocoding API to look up city coordinates.
// 'async/await' is JavaScript's equivalent of running a Task on a background thread
// and then processing the result back on the UI thread — but without the boilerplate.
// 'await' pauses execution of THIS function only; the rest of the page stays responsive.
async function geocodeSearch(query) {
    searchDropdown.innerHTML = `<div class="dd-loading"><div class="dd-spinner"></div>Searching…</div>`;
    searchDropdown.classList.add('open');
    if (!OWM_KEY) { searchDropdown.innerHTML = `<div class="dd-empty">No API key set</div>`; return; }
    try {
        // fetch() makes an HTTP GET request — like calling an HttpClient in Java.
        // The URL encodes the query with encodeURIComponent() — like URLEncoder.encode() in Java.
        // 'await' waits for the HTTP response before continuing.
        const res  = await fetch(`https://api.openweathermap.org/geo/1.0/direct?q=${encodeURIComponent(query)}&limit=6&appid=${OWM_KEY}`);
        // .json() parses the response body as JSON — like using Gson/Jackson in Java.
        // It also returns a Promise, so we await it too.
        const data = await res.json();
        if (!data || !data.length) { searchDropdown.innerHTML = `<div class="dd-empty">No results for "${query}"</div>`; return; }
        currentResults = data;
        highlightIndex = -1;
        renderDropdown(data, query);
    } catch(err) {
        searchDropdown.innerHTML = `<div class="dd-empty">Search failed</div>`;
    }
}

// Renders the search results list — like populating a ListView with custom cells.
// Array.map() transforms each result into an HTML string; .join('') concatenates them.
// data-index="..." stores the result index on each element — like setUserData() on a Node.
function renderDropdown(results, query) {
    const q = query.toLowerCase();
    searchDropdown.innerHTML = results.map((r, i) => {
        const flag = r.country ? countryFlag(r.country) : '🌍';
        const name = highlightMatch(r.name, q); // Wraps the matching substring in a styled <span>
        const sub  = [r.state, r.country].filter(Boolean).join(', '); // .filter(Boolean) removes null/undefined values
        return `<div class="dd-item" data-index="${i}">
                <div class="dd-flag">${flag}</div>
                <div class="dd-main">
                    <div class="dd-name">${name}</div>
                    ${sub ? `<div class="dd-sub">${sub}</div>` : ''}
                </div>
                <div class="dd-coords">${r.lat.toFixed(2)}°<br>${r.lon.toFixed(2)}°</div>
            </div>`;
    }).join('');
    // Attach click handlers to each rendered result item — like setCellFactory() with event handlers.
    searchDropdown.querySelectorAll('.dd-item').forEach((el, i) => {
        el.addEventListener('click', () => selectResult(currentResults[i]));
    });
}

// Wraps the matched portion of the city name in a <span> so CSS can highlight it.
// indexOf() / slice() work the same as in Java's String class.
function highlightMatch(text, query) {
    const idx = text.toLowerCase().indexOf(query);
    if (idx === -1) return text;
    return text.slice(0, idx) + `<span class="dd-match">${text.slice(idx, idx + query.length)}</span>` + text.slice(idx + query.length);
}

// Moves the map to the selected city and triggers a weather fetch.
// map.setView() pans/zooms the map — like animating a camera to new coordinates.
// setTimeout(..., 400) delays the weather popup until the pan animation finishes —
// like a Timeline or PauseTransition followed by an action in JavaFX.
function selectResult(result) {
    searchInput.value = [result.name, result.country].filter(Boolean).join(', ');
    searchClear.classList.add('visible');
    closeDropdown();
    map.setView([result.lat, result.lon], 9, { animate: true, duration: 0.8 });
    setTimeout(() => {
        // latLngToContainerPoint converts a geographic coordinate to a pixel position
        // within the map container — used to position the weather popup near the pin.
        const point = map.latLngToContainerPoint([result.lat, result.lon]);
        fetchWeather(result.lat, result.lon, point);
    }, 400);
}

// Converts a two-letter ISO country code to its flag emoji using Unicode regional indicators.
// e.g. "US" → 🇺🇸. Each letter maps to a Unicode code point offset from 0x1F1E6 ('🇦').
function countryFlag(code) {
    if (!code || code.length !== 2) return '🌍';
    return String.fromCodePoint(...code.toUpperCase().split('').map(c => 0x1F1E6 + c.charCodeAt(0) - 65));
}

// ── MAP CLICK ─────────────────────────────────────────────────────────────────
// map.on() registers an event listener on the Leaflet map — like map.setOnMouseClicked() in JavaFX.
// e.latlng gives the geographic coordinate clicked; e.containerPoint gives the pixel position
// within the map container — used to anchor the popup near the click.
let hintDismissed = false;
map.on('click', e => {
    if (!hintDismissed) {
        const hint = document.getElementById('click-hint');
        // .style.opacity = '0' fades out the hint — like FadeTransition to opacity 0.
        // The actual animated fade is handled by CSS transition rules in the stylesheet.
        if (hint) hint.style.opacity = '0';
        hintDismissed = true;
    }
    fetchWeather(e.latlng.lat, e.latlng.lng, e.containerPoint);
});

// ── FETCH WEATHER ─────────────────────────────────────────────────────────────
// Fetches current weather from the OpenWeatherMap API and renders a popup.
// fetch().then().then().catch() is the Promise-chaining style (older pattern) —
// functionally equivalent to async/await used earlier, but written as a chain
// of callbacks rather than sequential code. Compare to Java's CompletableFuture chain.
function fetchWeather(lat, lng, point) {
    showPopupLoading(point || { x: window.innerWidth / 2, y: window.innerHeight / 2 });
    if (!OWM_KEY) { document.getElementById('popup-content').innerHTML = `<div class="popup-loading">No API key set</div>`; return; }
    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&units=${units}&appid=${OWM_KEY}`)
        .then(r => r.json())         // First .then() — parses the JSON response body
        .then(data => {
            if (data.cod !== 200) throw new Error(data.message || 'API error'); // cod is the OWM status code
            renderWeatherPopup(data, point); // Success — render the data
        })
        .catch(err => {             // .catch() handles errors from any step above — like try/catch around the chain
            document.getElementById('popup-content').innerHTML = `<div class="popup-loading">⚠ ${err.message}</div>`;
        });
}

// Shows the popup with a spinner while data is loading —
// like switching a StackPane to a progress indicator layer.
// popup.classList.add('visible') triggers a CSS transition that animates it in —
// the 'visible' class is defined in CSS with opacity:1 and a transition rule.
function showPopupLoading(point) {
    document.getElementById('popup-content').innerHTML = `<div class="popup-loading"><div class="spinner"></div>Loading…</div>`;
    positionPopup(point);
    popup.classList.add('visible');
}

// Positions the popup near the clicked point while keeping it within the viewport.
// window.innerWidth/innerHeight is the visible browser area — like Stage.getWidth()/getHeight().
// This is manual bounds-checking to prevent the popup from overflowing off-screen —
// equivalent to computing a tooltip position relative to screen bounds in JavaFX.
function positionPopup(point) {
    if (!point) return;
    const W = 256, H = 240, PAD = 8, TOP = 56; // Popup dimensions and safe margins in pixels
    let left = point.x + 12;
    let top  = point.y - 16;
    if (left + W > window.innerWidth  - PAD) left = point.x - W - 12; // Flip to left if too close to right edge
    if (top  + H > window.innerHeight - PAD) top  = window.innerHeight - H - PAD; // Clamp to bottom edge
    if (top < TOP) top = TOP; // Clamp to top edge (below the toolbar)
    // .style.left/top set absolute CSS pixel positions — like setLayoutX()/setLayoutY() in JavaFX.
    // Template literal adds 'px' unit suffix, which CSS requires (unlike JavaFX coordinates).
    popup.style.left = `${left}px`;
    popup.style.top  = `${top}px`;
}

// Builds and injects the weather popup content — like populating labels and grids in a JavaFX FXML view.
// All the data is pulled from the OWM API response object 'd'.
// toFixed(1) formats a float to 1 decimal place — like String.format("%.1f", value) in Java.
function renderWeatherPopup(d, point) {
    const metric   = units === 'metric';
    const temp     = Math.round(d.main.temp);       // Math.round() works the same as in Java
    const feels    = Math.round(d.main.feels_like);
    const tempUnit = metric ? '°C' : '°F';
    const windUnit = metric ? 'm/s' : 'mph';
    const wind     = metric ? d.wind.speed.toFixed(1) : (d.wind.speed * 2.237).toFixed(1); // Convert m/s to mph if needed
    const vis      = d.visibility ? (metric ? `${(d.visibility/1000).toFixed(1)} km` : `${(d.visibility/1609).toFixed(1)} mi`) : '–';
    const icon     = getWeatherEmoji(d.weather[0].id); // d.weather is an array; [0] gets the first element

    // Sets the entire popup HTML in one shot — like calling setText/setValue on every control
    // in a form. In a JavaFX app this would typically be done by binding properties or
    // calling individual setters; here it's a single innerHTML assignment for brevity.
    document.getElementById('popup-content').innerHTML = `
            <div class="popup-header">
                <div class="popup-icon">${icon}</div>
                <div>
                    <div class="popup-city">${d.name || '–'}</div>
                    <div class="popup-country">${d.sys?.country || ''} · ${d.coord.lat.toFixed(2)}°, ${d.coord.lon.toFixed(2)}°</div>
                </div>
                <div class="popup-temp">${temp}${tempUnit}</div>
            </div>
            <div class="popup-desc">${d.weather[0].description}</div>
            <div class="popup-grid">
                <div class="popup-stat"><div class="popup-stat-label">Feels like</div><div class="popup-stat-value">${feels}${tempUnit}</div></div>
                <div class="popup-stat"><div class="popup-stat-label">Humidity</div><div class="popup-stat-value">${d.main.humidity}%</div></div>
                <div class="popup-stat"><div class="popup-stat-label">Wind</div><div class="popup-stat-value">${wind} ${windUnit}</div></div>
                <div class="popup-stat"><div class="popup-stat-label">Pressure</div><div class="popup-stat-value">${d.main.pressure} hPa</div></div>
                <div class="popup-stat"><div class="popup-stat-label">Visibility</div><div class="popup-stat-value">${vis}</div></div>
                <div class="popup-stat"><div class="popup-stat-label">Cloud cover</div><div class="popup-stat-value">${d.clouds.all}%</div></div>
            </div>`;
    positionPopup(point);
}

// Hides the popup — like setting a node's visibility to false in JavaFX.
// Removing the 'visible' CSS class triggers a CSS opacity/transform transition defined
// in the stylesheet, creating a smooth hide animation without any explicit animation code.
function closePopup() { popup.classList.remove('visible'); }

// Maps OWM weather condition codes to emoji icons.
// OWM condition codes are numeric ranges (e.g. 500-504 = rain).
// This is equivalent to a switch expression or a series of if/else-if blocks —
// which is exactly what this is, written as a chain of ternaries for compactness.
function getWeatherEmoji(code) {
    if (code >= 200 && code < 300) return '⛈';   // Thunderstorm
    if (code >= 300 && code < 400) return '🌦';   // Drizzle
    if (code >= 500 && code < 504) return '🌧';   // Rain
    if (code === 511)               return '🌨';   // Freezing rain
    if (code >= 520 && code < 532) return '🌦';   // Shower rain
    if (code >= 600 && code < 700) return '❄';   // Snow
    if (code >= 700 && code < 800) return '🌫';   // Atmosphere (fog, haze, etc.)
    if (code === 800)               return '☀';   // Clear sky
    if (code === 801)               return '🌤';   // Few clouds
    if (code === 802)               return '⛅';   // Scattered clouds
    if (code === 803 || code === 804) return '☁'; // Broken/overcast clouds
    return '🌡';                                   // Fallback
}

// ── STATUS ────────────────────────────────────────────────────────────────────
// Updates a status bar with the current mouse coordinate as the cursor moves.
// 'mousemove' fires continuously during mouse movement — like a MouseEvent.MOUSE_MOVED handler.
// 'mouseout' fires when the cursor leaves the map element — like MOUSE_EXITED in JavaFX.
map.on('mousemove', e => {
    document.getElementById('status-text').textContent =
        `${e.latlng.lat.toFixed(3)}°, ${e.latlng.lng.toFixed(3)}°`;
});
map.on('mouseout', () => {
    document.getElementById('status-text').textContent = 'Click map for weather';
});

// Global keyboard shortcut to close the popup with Escape —
// like a scene-level KeyEvent handler in JavaFX (scene.setOnKeyPressed(...)).
document.addEventListener('keydown', e => { if (e.key === 'Escape') closePopup(); });
package org.patman101.weather_app.frontend.ui.themes;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;

public final class Themes {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public static final Theme LIGHT = new Theme("light", new PrimerLight().getUserAgentStylesheet(), "/styles/icons/settings/sun.png");

    public static final Theme DARK = new Theme("dark", new PrimerDark().getUserAgentStylesheet(), "/styles/icons/settings/moon.png");

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private Themes() {}
}

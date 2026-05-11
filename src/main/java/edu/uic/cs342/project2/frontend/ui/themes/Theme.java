package edu.uic.cs342.project2.frontend.ui.themes;

import javafx.application.Application;

public class Theme {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private final String name, stylesheet, iconPath;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    protected Theme(String name, String stylesheet, String iconPath) {
        this.name = name;
        this.stylesheet = stylesheet;
        this.iconPath = iconPath;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getIconPath() { return this.iconPath; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return this.name; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Theme)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Theme other = (Theme) object;
        return this.name.equals(other.name);
    }

    public void apply() { Application.setUserAgentStylesheet(this.stylesheet); }
}

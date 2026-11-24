package com.spacesmp.welcomedialog.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DialogConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private String title = "&6Welcome to the Server!";
    private String body = "&fThanks for joining! Check out our links below.";
    private ButtonsConfig buttons = new ButtonsConfig();

    public static DialogConfig load(Path path, Logger logger) {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            DialogConfig loaded = GSON.fromJson(reader, DialogConfig.class);
            if (loaded == null) {
                logger.warning("config.json is empty; using default configuration.");
                return new DialogConfig();
            }
            loaded.applyDefaults();
            return loaded;
        } catch (IOException | JsonParseException ex) {
            logger.log(Level.WARNING, "Failed to load config.json; using default configuration.", ex);
            return new DialogConfig();
        }
    }

    private void applyDefaults() {
        if (this.title == null) {
            this.title = "&6Welcome to the Server!";
        }
        if (this.body == null) {
            this.body = "&fThanks for joining! Check out our links below.";
        }
        if (this.buttons == null) {
            this.buttons = new ButtonsConfig();
        }
        this.buttons.applyDefaults();
    }

    public String title() {
        return this.title;
    }

    public String body() {
        return this.body;
    }

    public ButtonsConfig buttons() {
        return this.buttons;
    }

    public static final class ButtonsConfig {
        private LinkButtonConfig discord = new LinkButtonConfig("&9Join our Discord", "https://discord.gg/example");
        private ExploreButtonConfig explore = new ExploreButtonConfig("&aStart Exploring");
        private LinkButtonConfig website = new LinkButtonConfig("&eVisit our Website", "https://example.com");

        private void applyDefaults() {
            if (this.discord == null) {
                this.discord = new LinkButtonConfig("&9Join our Discord", "https://discord.gg/example");
            } else {
                this.discord.applyDefaults("&9Join our Discord", "https://discord.gg/example");
            }
            if (this.explore == null) {
                this.explore = new ExploreButtonConfig("&aStart Exploring");
            } else {
                this.explore.applyDefaults("&aStart Exploring");
            }
            if (this.website == null) {
                this.website = new LinkButtonConfig("&eVisit our Website", "https://example.com");
            } else {
                this.website.applyDefaults("&eVisit our Website", "https://example.com");
            }
        }

        public LinkButtonConfig discord() {
            return this.discord;
        }

        public ExploreButtonConfig explore() {
            return this.explore;
        }

        public LinkButtonConfig website() {
            return this.website;
        }
    }

    public static final class LinkButtonConfig {
        private String label;
        private String url;

        public LinkButtonConfig() {
        }

        public LinkButtonConfig(String label, String url) {
            this.label = label;
            this.url = url;
        }

        private void applyDefaults(String defaultLabel, String defaultUrl) {
            if (this.label == null) {
                this.label = defaultLabel;
            }
            if (this.url == null) {
                this.url = defaultUrl;
            }
        }

        public String label() {
            return this.label;
        }

        public String url() {
            return this.url;
        }
    }

    public static final class ExploreButtonConfig {
        private String label;

        public ExploreButtonConfig() {
        }

        public ExploreButtonConfig(String label) {
            this.label = label;
        }

        private void applyDefaults(String defaultLabel) {
            if (this.label == null) {
                this.label = defaultLabel;
            }
        }

        public String label() {
            return this.label;
        }
    }
}

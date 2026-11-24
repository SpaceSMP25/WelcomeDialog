package com.spacesmp.welcomedialog.dialog;

import com.spacesmp.welcomedialog.config.DialogConfig;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

public final class WelcomeDialogFactory {

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    private WelcomeDialogFactory() {
    }

    public static Dialog create(DialogConfig config, Logger logger) {
        ActionButton discordButton = createUrlButton(
                config.buttons().discord().label(),
                config.buttons().discord().url(),
                "discord",
                logger
        );
        ActionButton websiteButton = createUrlButton(
                config.buttons().website().label(),
                config.buttons().website().url(),
                "website",
                logger
        );
        ActionButton exploreButton = ActionButton.builder(colorize(config.buttons().explore().label()))
                .build();

        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(colorize(config.title()))
                        .canCloseWithEscape(false)
                        .afterAction(DialogBase.DialogAfterAction.CLOSE)
                        .body(List.of(DialogBody.plainMessage(colorize(config.body()))))
                        .build())
                .type(DialogType.multiAction(
                        List.of(discordButton, websiteButton),
                        exploreButton,
                        2
                ))
        );
    }

    private static ActionButton createUrlButton(String label, String url, String buttonName, Logger logger) {
        ActionButton.Builder builder = ActionButton.builder(colorize(label));
        if (isValidHttpUrl(url)) {
            builder.action(DialogAction.staticAction(ClickEvent.openUrl(url)));
        } else {
            logger.warning("Invalid URL for button '" + buttonName + "': '" + url + "'. Expected http:// or https://.");
        }
        return builder.build();
    }

    private static boolean isValidHttpUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        try {
            URI uri = URI.create(url.trim());
            String scheme = uri.getScheme();
            return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static Component colorize(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }
        return LEGACY_SERIALIZER.deserialize(text);
    }
}

package com.example.hidealch;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("hidealch")
public interface HideAlchConfig extends Config {

    @ConfigItem(
            position = 0,
            keyName = "items",
            name = "Items",
            description = "Items to hide alch option",
            hidden = true
    )
    default String getItems() {
        return "";
    }

    void setItems(String value);

    @ConfigItem(
            position = 1,
            keyName = "enable",
            name = "Enable",
            description = "Enable or disable the Hide Alch plugin"
    )
    default boolean isEnabled() {
        return false;
    }

    @ConfigItem(
            position = 2,
            keyName = "updateItems",
            name = "Update Hidden Items",
            description = "Update the list of hidden items"
    )
    default Button updateItems() {
        return new Button();
    }
}

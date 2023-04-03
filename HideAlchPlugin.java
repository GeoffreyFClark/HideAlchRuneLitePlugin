package com.example.hidealch;

import com.google.inject.Provides;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
    name = "Hide Alchemy",
    description = "Hides the 'High Alchemy' option on specified items",
    tags = {"hide", "alchemy", "high", "alch"}
)
public class HideAlchPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private HideAlchConfig config;

    private Set<Integer> hiddenItems;

    @Provides
    HideAlchConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(HideAlchConfig.class);
    }

    @Override
    protected void startUp() {
        updateHiddenItems();
    }

    @Override
    protected void shutDown() {
        // Clean up if necessary
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event) {
        if (hiddenItems == null) {
            return;
        }

        if (event.getOption().equals("Cast") && event.getTarget().contains("High Alchemy") && hiddenItems.contains(event.getIdentifier())) {
            client.setMenuEntries(Arrays.stream(client.getMenuEntries())
                    .filter(entry -> entry != event.getMenuEntry())
                    .toArray(net.runelite.api.MenuEntry[]::new));
        }
    }

    private void updateHiddenItems() {
        hiddenItems = new HashSet<>();
        String[] itemIds = config.getItems().split(",");
        for (String itemId : itemIds) {
            try {
                hiddenItems.add(Integer.parseInt(itemId.trim()));
            } catch (NumberFormatException e) {
                // Ignore invalid IDs
            }
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if (event.getGroup().equals("hidealch")) {
            updateHiddenItems();
        }
    }
}

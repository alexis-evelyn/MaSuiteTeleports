package fi.matiaspaavilainen.masuiteteleports.bungee;

import fi.matiaspaavilainen.masuitecore.bungee.chat.Formator;
import fi.matiaspaavilainen.masuitecore.core.Updator;
import fi.matiaspaavilainen.masuitecore.core.api.MaSuiteCoreAPI;
import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import fi.matiaspaavilainen.masuiteteleports.bungee.listeners.PlayerJoinEvent;
import fi.matiaspaavilainen.masuiteteleports.bungee.listeners.PlayerQuitEvent;
import fi.matiaspaavilainen.masuiteteleports.bungee.listeners.PositionListener;
import fi.matiaspaavilainen.masuiteteleports.bungee.listeners.TeleportMessageListener;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class MaSuiteTeleports extends Plugin implements Listener {

    public BungeeConfiguration config = new BungeeConfiguration();
    public Formator formator = new Formator();
    public static HashMap<UUID, Long> cooldowns = new HashMap<>();
    public PositionListener positions = new PositionListener(this);

    public MaSuiteCoreAPI api = new MaSuiteCoreAPI();

    @Override
    public void onEnable() {

        // Generate configs
        config.create(this, "teleports", "messages.yml");
        config.create(this, "teleports", "settings.yml");
        config.create(this, "teleports", "buttons.yml");

        // Register listeners
        getProxy().getPluginManager().registerListener(this, new TeleportMessageListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerJoinEvent(this));
        getProxy().getPluginManager().registerListener(this, new PlayerQuitEvent());
        // Check updates
        new Updator(getDescription().getVersion(), getDescription().getName(), "60125").checkUpdates();

        config.addDefault("teleports/messages.yml", "receiver.teleported", "&7You have been teleported to &b%player%&7!");
        config.addDefault("teleports/messages.yml", "sender.teleported", "&9%player%&7 has been teleported to you!");
        config.addDefault("teleports/messages.yml", "tptoggle.on", "&cYou are now denying force teleportations!");
        config.addDefault("teleports/messages.yml", "tptoggle.off", "&aYou are now allowing force teleportations!");
        config.addDefault("teleports/messages.yml", "tptoggle.disabled", "&c%player has disabled force teleportations!");
        config.addDefault("teleports/settings.yml", "teleport-delay", 750);
    }
}
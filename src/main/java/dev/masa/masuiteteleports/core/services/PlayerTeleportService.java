package dev.masa.masuiteteleports.core.services;

import dev.masa.masuitecore.core.channels.BungeePluginChannel;
import dev.masa.masuiteteleports.bungee.MaSuiteTeleports;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.concurrent.TimeUnit;

public class PlayerTeleportService {

    private MaSuiteTeleports plugin;

    public PlayerTeleportService(MaSuiteTeleports plugin) {
        this.plugin = plugin;
    }

    /**
     * Teleport player to target
     *
     * @param player player to teleport
     * @param target target to use
     */
    public void teleportPlayerToPlayer(ProxiedPlayer player, ProxiedPlayer target) {
        BungeePluginChannel bpc = new BungeePluginChannel(plugin, target.getServer().getInfo(),
                "MaSuiteTeleports",
                "PlayerToPlayer",
                player.getName(),
                target.getName()
        );
        if (!player.getServer().getInfo().getName().equals(target.getServer().getInfo().getName())) {
            player.connect(plugin.getProxy().getServerInfo(target.getServer().getInfo().getName()));
            plugin.getProxy().getScheduler().schedule(plugin, bpc::send, plugin.config.load(null, "config.yml").getInt("teleportation-delay"), TimeUnit.MILLISECONDS);
        } else {
            bpc.send();
        }
    }
}
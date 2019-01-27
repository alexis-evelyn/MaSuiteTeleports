package fi.matiaspaavilainen.masuiteteleports.bukkit.commands.force;

import fi.matiaspaavilainen.masuitecore.core.channels.BukkitPluginChannel;
import fi.matiaspaavilainen.masuiteteleports.bukkit.MaSuiteTeleports;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpHereCommand implements CommandExecutor {

    private MaSuiteTeleports plugin;

    public TpHereCommand(MaSuiteTeleports p) {
        plugin = p;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (args.length != 1) {
                plugin.formator.sendMessage(sender, plugin.config.load("teleports", "syntax.yml").getString("tphere"));
                return;
            }

            if (plugin.in_command.contains(sender)) {
                plugin.formator.sendMessage(sender, plugin.config.load(null, "messages.yml").getString("on-active-command"));
                return;
            }

            plugin.in_command.add(sender);
            Player p = (Player) sender;
            new BukkitPluginChannel(plugin, p, new Object[]{"MaSuiteTeleports", "TeleportForceHere", sender.getName(), args[0], p.hasPermission("masuiteteleports.teleport.toggle.bypass")}).send();
            plugin.in_command.remove(sender);

        });

        return true;
    }
}

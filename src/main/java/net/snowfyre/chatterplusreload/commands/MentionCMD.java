package net.snowfyre.chatterplusreload.commands;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static net.snowfyre.chatterplusreload.ChatterPlusReload.prefix;
import static net.snowfyre.chatterplusreload.Utils.color;

public class MentionCMD implements CommandExecutor {
    public static HashMap<Player, Boolean> enabled = new HashMap<Player, Boolean>();
    private final ChatterPlusReload plugin;

    public MentionCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    public void togglePluginState(Player player) {

        if (enabled.containsKey(player)) {
            if (enabled.get(player)) {
                enabled.put(player, false);
                player.sendMessage(prefix + ChatColor.RED + "Pings disabled.");

            } else {

                enabled.put(player, true);
                player.sendMessage(prefix + ChatColor.GREEN + "Pings enabled.");

            }

        } else {
            enabled.put(player, false);
            player.sendMessage(prefix + ChatColor.RED + "Pings disabled.");
        }
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mention")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (args.length == 0) {

                    if (player.hasPermission("cplusreloaded.mention")) {

                        togglePluginState(player);

                    } else {
                        player.sendMessage(color(plugin.getConfig().getString("messages.noperms")));
                    }

                }
            }
        }
        return true;
    }
}

package net.snowfyre.chatterplusreload.commands;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.snowfyre.chatterplusreload.Utils.color;

public class CPlusCMD implements CommandExecutor {
    private final ChatterPlusReload plugin;

    public CPlusCMD(ChatterPlusReload plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 0) {
                if (p.hasPermission("cplusreloaded.view")) {
                    p.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "               [" + ChatColor.GOLD + "" + ChatColor.BOLD + "CPlusReloaded" + ChatColor.GRAY + "" + ChatColor.BOLD + "] ");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/cplus" + ChatColor.GRAY + " - Shows the list of commands");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/cplus reload" + ChatColor.GRAY + " - Reloads the config");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/broadcast" + ChatColor.GRAY + " - Broadcasts a message to the whole server");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/chatclear" + ChatColor.GRAY + " - Clear's the chat");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/lockchat" + ChatColor.GRAY + " - lock's the chat");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/unlockchat" + ChatColor.GRAY + " - unlock's the chat");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/mention" + ChatColor.GRAY + " - Toggle the ability to mention and to be mentioned");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/staffchat" + ChatColor.GRAY + " - a separate chat channel for staff!");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/sudoall" + ChatColor.GRAY + " - sudo all the players in the server");
                    p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.RED + "/sudo" + ChatColor.GRAY + " - sudo a specific player in the server");
                } else {
                    p.sendMessage(color(plugin.getConfig().getString("messages.noperms")));
                }

            } else if (args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("cplusreloaded.reload")) {
                    plugin.reloadConfig();
                    p.sendMessage(ChatColor.GREEN + "Reloaded the config of ChatterPlus");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                } else {
                    p.sendMessage(color(plugin.getConfig().getString("messages.noperms")));
                }

            } else {
                p.sendMessage(ChatColor.RED + "Please enter a valid command!");
            }

            return true;

        }
        return true;
    }
}

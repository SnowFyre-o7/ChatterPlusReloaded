package net.snowfyre.chatterplusreload.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.snowfyre.chatterplusreload.Utils.color;

public class ClearChatCMD implements CommandExecutor {

    private final ChatterPlusReload plugin;

    public ClearChatCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.chatclear")) {
                for (int i = 0; i < 100; ++i) {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.sendMessage(ChatColor.BLUE + "");

                    }
                }
                String chatclear = plugin.getConfig().getString("messages.chat-cleared-global");
                for (Player people : Bukkit.getOnlinePlayers()) {
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        chatclear = PlaceholderAPI.setPlaceholders(p, chatclear);
                        people.sendMessage(color(chatclear).replace("%player_name%", p.getDisplayName()));

                    } else {
                    p.sendMessage(color(plugin.getConfig().getString("messages.noperms")));
                }
            }
            } else {
                System.out.println("Sorry, but you can only execute /chatclear as a player.");
            }
            return true;
        }
        return true;
    }
}

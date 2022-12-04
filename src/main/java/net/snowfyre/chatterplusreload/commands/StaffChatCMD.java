package net.snowfyre.chatterplusreload.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.snowfyre.chatterplusreload.Utils.color;

public class StaffChatCMD implements CommandExecutor {

    private final ChatterPlusReload plugin;

    public StaffChatCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.staffchat")) {
                if (args.length >= 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.hasPermission(plugin.getConfig().getString("staff-chat-permission"))) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                sb.append(args[i]).append(" ");
                            }
                            String staffchatformat = plugin.getConfig().getString("staff-chat-format");
                            String message = sb.toString().trim();
                            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                                staffchatformat = PlaceholderAPI.setPlaceholders(p, staffchatformat);
                                all.sendMessage(color(staffchatformat).replace("%message%", message).replace("%player_name%", p.getDisplayName()));
                        }
                     else{
                        p.sendMessage(color(plugin.getConfig().getString("staffchat-less-args")));
                    }
                } else {
                    p.sendMessage(color(plugin.getConfig().getString("noperms")));
                }


            } }
            return true;
        }
    }
        return false;
    } }

package net.snowfyre.chatterplusreload.commands;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.snowfyre.chatterplusreload.Utils.color;

public class BroadcastCMD implements CommandExecutor {

    private final ChatterPlusReload plugin;

    public BroadcastCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.broadcast")) {
                if (args.length == 0) {
                    p.sendMessage(color(plugin.getConfig().getString("messages.broadcast-less-args")));
                } else if (args.length >= 1) {

                    for (Player target : Bukkit.getOnlinePlayers()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }

                        String message = sb.toString().trim();
                        target.sendMessage(color(plugin.getConfig().getString("broadcast-format").replace("%broadcast_msg%", message)));
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    }

                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.noperms")));
            }

        }

        return true;
    }
    }

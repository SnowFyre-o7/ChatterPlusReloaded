package net.snowfyre.chatterplusreload.commands;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCMD implements CommandExecutor {
    private final ChatterPlusReload plugin;

    public SudoCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.sudo")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sudo-less-args")));
                } else if (args.length == 1) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sudo-less-args")));
                } else {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target != null && target.isOnline()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }
                        String message = sb.toString().trim();
                        target.chat(message);
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sudo-player-offline")));
                    }
                }


            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.noperms")));
            }


            return true;
        }
        return true;
    }
}

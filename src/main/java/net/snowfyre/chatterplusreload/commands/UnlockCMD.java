package net.snowfyre.chatterplusreload.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.snowfyre.chatterplusreload.ChatterPlusReload;
import net.snowfyre.chatterplusreload.listeners.ChatLockListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.snowfyre.chatterplusreload.Utils.color;

public class UnlockCMD implements CommandExecutor {

    private final ChatterPlusReload plugin;

    public UnlockCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            String unlockchat = plugin.getConfig().getString("messages.chat-unlock-success-all");
            Player p = (Player) sender;
            if (p.hasPermission("cplusreloaded.lockchat")) {
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    unlockchat = PlaceholderAPI.setPlaceholders(p, unlockchat);
                    ChatLockListener.isChatLocked = false;
                    p.sendMessage(color(unlockchat).replace("%player_name%", p.getDisplayName()));
                } else {
                    p.sendMessage(color(plugin.getConfig().getString("messages.noperms")));
                }
            }
        } else {
            System.out.println("This command can only be executed by a player");
        }
        return true;
    }
}

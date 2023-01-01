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

public class LockCMD implements CommandExecutor {

    private final ChatterPlusReload plugin;

    public LockCMD(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            String lockchat = plugin.getConfig().getString("messages.chat-lock-success-all");
            Player player = (Player) sender;
            if (player.hasPermission("cplusreloaded.lockchat")) {
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    lockchat = PlaceholderAPI.setPlaceholders(player, lockchat);
                    ChatLockListener.isChatLocked = true;
                    player.sendMessage(color(lockchat).replace("%player_name%", player.getDisplayName()));
                } else {
                    player.sendMessage(color(plugin.getConfig().getString("messages.noperms")));
                }

            }
        } else{
            System.out.println("This command can only be executed by a player");
        }
        return true;
    }
}


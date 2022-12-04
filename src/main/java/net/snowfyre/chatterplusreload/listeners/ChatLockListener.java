package net.snowfyre.chatterplusreload.listeners;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import net.snowfyre.chatterplusreload.commands.LockCMD;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import net.snowfyre.chatterplusreload.ChatterPlusReload;

public class ChatLockListener implements Listener {
    public static boolean isChatLocked = false;

    private final ChatterPlusReload plugin;

    public ChatLockListener(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void LockChecker(AsyncPlayerChatEvent e) {
        if (isChatLocked == true) {
            Player p = e.getPlayer();


            if (!p.hasPermission("cplusreloaded.bypasschatlock")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.message-during-lock")));
                e.setCancelled(true);
            }
        }
    }

}

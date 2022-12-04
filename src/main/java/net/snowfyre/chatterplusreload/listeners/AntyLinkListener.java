package net.snowfyre.chatterplusreload.listeners;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntyLinkListener implements Listener {
    private final ChatterPlusReload plugin;

    public AntyLinkListener(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void Antylink(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        Player p = e.getPlayer();
        if (message.contains("https://") || message.contains(".org") || message.contains(".com") || message.contains(".net") || message.contains(".pl") || message.contains(".tk") || message.contains(".ml") || message.contains(".eu") || message.contains(".xyz") || message.contains(".party") || message.contains(".us") || message.contains(".de") || message.contains(".biz") || message.contains(".art")) {
            if (!p.hasPermission("cplusreloaded.bypassantylink")) {
                if (plugin.getConfig().getBoolean("antylink-remove-dots")) {

                    e.setMessage(message.replace(".", " "));

                } else if (plugin.getConfig().getBoolean("antylink-cancel-send-if-link-detected")) {
                    e.setCancelled(true);

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.contains-link")));
                }
            }

        }
    }

}

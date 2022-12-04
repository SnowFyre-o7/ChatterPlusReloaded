package net.snowfyre.chatterplusreload.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.snowfyre.chatterplusreload.ChatterPlusReload;
import net.snowfyre.chatterplusreload.commands.MentionCMD;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PingListener implements Listener {
    private final ChatterPlusReload plugin;

    public PingListener(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        for (Player pinged : Bukkit.getServer().getOnlinePlayers()) {

            if (event.getMessage().contains("@" + pinged.getName())) {
                if (MentionCMD.enabled.get(player)) {
                    if (pinged.hasPermission("cplusreloaded.mention")) {

                        String msg = event.getMessage().replace("@" + pinged.getName(), ChatColor.GOLD + pinged.getName() + ChatColor.RESET);
                        event.setMessage(msg);

                    }
                    pinged.getWorld().playSound(pinged.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 3.0F, 0.5F);
                    pinged.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "✯ You have been pinged by " + player.getDisplayName() + " ✯"));

                    return;
                }
            }
        }
    }
}

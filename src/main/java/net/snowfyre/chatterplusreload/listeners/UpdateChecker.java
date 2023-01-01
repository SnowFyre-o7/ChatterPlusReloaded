package net.snowfyre.chatterplusreload.listeners;

import net.snowfyre.chatterplusreload.ChatterPlusReload;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateChecker implements Listener {
    private final ChatterPlusReload plugin;

    public UpdateChecker(ChatterPlusReload plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void UpdateChecker(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            if (p.hasPermission("cplusreloaded.check-updates{")) {
                HttpURLConnection con = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=106347").openConnection();
                con.setRequestMethod("GET");
                String onlineVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                boolean availableUpdate = !onlineVersion.equals(plugin.getDescription().getVersion());

                if (availableUpdate) {
                    p.sendMessage(ChatColor.GREEN + "New version of ChatterPlus available: " + onlineVersion);
                }
            }
        } catch (MalformedURLException exception) {

        } catch (IOException exception) {

        }
    }
}

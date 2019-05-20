package me.markiscool.usersapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private UsersAPI api;

    public PlayerJoinListener() {
        api = UsersAPIPlugin.getInstance();
    }

    /**
     * Registers players if they are not already registered.
     * Also checks for name change
     * @param event -
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if(!api.isRegistered(player)) {
            api.register(player);
        } else if(!((String) api.getValue(player, "username")).equalsIgnoreCase(player.getName())) {
            api.setValue(player, "username", player.getName());
        }
    }

}

package me.markiscool.usersapi;

import org.bukkit.plugin.java.JavaPlugin;

public class UsersAPIPlugin extends JavaPlugin {

    private static UsersAPI api;

    @Override
    public void onEnable() {
        api = new UsersAPI(this);
        getConfig().addDefault("delay", 20); //delay in ticks (20 ticks = 1 second)
        final long delay = getConfig().contains("delay") ? getConfig().getLong("delay") : 20;
        new SaveScheduler().runTaskTimer(this, 60, delay);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        api.saveConfig();
    }

    public static UsersAPI getInstance() {
        return api;
    }

}

package me.markiscool.usersapi;

import org.bukkit.plugin.java.JavaPlugin;

public class UsersAPIPlugin extends JavaPlugin {

    private static UsersAPI api;

    @Override
    public void onEnable() {
        api = new UsersAPI(this);
    }

    @Override
    public void onDisable() {
        api.saveConfig();
    }

    public static UsersAPI getInstance() {
        return api;
    }

}

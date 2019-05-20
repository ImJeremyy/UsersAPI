package me.markiscool.usersapi;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveScheduler extends BukkitRunnable {

    private UsersAPI api;

    public SaveScheduler() {
        api = UsersAPIPlugin.getInstance();
    }

    @Override
    public void run() {
        api.saveConfig();
    }

}

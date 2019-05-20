package me.markiscool.usersapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UsersAPI {

    private UsersAPIPlugin plugin;

    private File file;
    private FileConfiguration config;

    /**
     * Do not use this constructor
     */
    public UsersAPI(final UsersAPIPlugin plugin) {
        this.plugin = plugin;
        createFile();
    }

    public OfflinePlayer getOfflinePlayer(final String username) {
        for(final String uuid : config.getConfigurationSection("users").getKeys(false)) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            if(offlinePlayer.getName().equalsIgnoreCase(ChatColor.stripColor(username))) return offlinePlayer;
        }
        return null;
    }

    public Object getValue(final OfflinePlayer player, String name) {
        return config.get("users." + player.getUniqueId() + "." + name);
    }

    public void setValue(final OfflinePlayer player, String name, Object value) {
        config.set("users." + player.getUniqueId() + ".name", value);
        saveConfig();
    }

    private void createFile() {
        file = new File(plugin.getDataFolder(), "users.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().warning("users.yml could not be created");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        if(!config.contains("users")) {
            config.createSection("users");
            saveConfig();
        }
    }

    /**
     * May cause major lag in server.
     */
    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("users.yml could not be saved");
        }
    }

}

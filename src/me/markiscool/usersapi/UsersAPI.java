package me.markiscool.usersapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
            final String readUsername = config.getString("users." + uuid + ".username");
            if(readUsername.equalsIgnoreCase(ChatColor.stripColor(username))) return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        }
        return null;
    }

    public Object getValue(final OfflinePlayer player, String name) {
        return config.get("users." + player.getUniqueId().toString() + "." + name);
    }

    public void setValue(final OfflinePlayer player, String name, Object value) {
        config.set("users." + player.getUniqueId().toString() + ".name", value);
    }

    public boolean isRegistered(final Player player) {
        return config.contains("users." + player.getUniqueId().toString());
    }

    public void register(final Player player) {
        config.set("users." + player.getUniqueId().toString() + ".username", player.getName());
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

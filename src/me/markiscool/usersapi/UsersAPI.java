package me.markiscool.usersapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
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

    /**
     * Gets you the OfflinePlayer object based on the username given.
     * Scans our users.yml file
     * @param username Username of player
     * @return OfflinePlayer object, or null if not found.
     */
    public OfflinePlayer getOfflinePlayer(final String username) {
        for(final String uuid : config.getConfigurationSection("users").getKeys(false)) {
            final String readUsername = config.getString("users." + uuid + ".username");
            if(readUsername.equalsIgnoreCase(ChatColor.stripColor(username))) return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        }
        return null;
    }

    /**
     * Get a value of a record. E.g., "username" is a potential value.
     * @param player player to check
     * @param name name of value
     * @return value, or null
     */
    public Object getValue(final OfflinePlayer player, String name) {
        return config.get("users." + player.getUniqueId().toString() + "." + name);
    }

    /**
     * Set a value of a record. E.g., "username" is a value which is given the player's name
     * @param player player to check
     * @param name name of value
     * @param value value, or null
     */
    public void setValue(final OfflinePlayer player, String name, Object value) {
        config.set("users." + player.getUniqueId().toString() + ".name", value);
    }

    /**
     * Checks if Player is registered
     * @param player player to check
     * @return true if registered
     */
    public boolean isRegistered(final Player player) {
        return config.contains("users." + player.getUniqueId().toString());
    }

    /**
     * Checks if UUID is registered
     * @param uuid uuid to check
     * @return true if registered
     */
    public boolean isRegistered(final UUID uuid) {
        return config.contains("users." + uuid.toString());
    }

    /**
     * Checks if String username is registered
     * Warning: This method loops through each record, use the other two isRegistered() methods for faster
     * speeds and less lag
     * @param username username to check
     * @return true if registered
     */
    public boolean isRegistered(final String username) {
        for(final String uuid : config.getConfigurationSection("users").getKeys(false)) {
            if(config.getString("users." + uuid + ".username").equals(username)) {
               return true;
            }
        }
        return false;
    }

    /**
     * Registers a player into the records
     * @param player player to register
     */
    public void register(final Player player) {
        config.set("users." + player.getUniqueId().toString() + ".username", player.getName());
    }

    /**
     * Attempts to create the file, if not already created.
     * Also intiializes the config file and its data.
     */
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
     * Saves config object and updates the .yml file
     * May cause major lag in server.
     */
    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("users.yml could not be saved");
        }
    }

    /**
     * Reloads object for up-to-date data and pulls from .yml file
     */
    public void loadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

}

package me.markiscool.usersapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UsersAPISetIntervalCommand implements CommandExecutor {

    private UsersAPIPlugin plugin;

    public UsersAPISetIntervalCommand(final UsersAPIPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.isOp()) {
            if(args.length == 1)  {
                try {
                    final long delay = Long.parseLong(args[0]);
                    Bukkit.getScheduler().cancelTasks(plugin);
                    Bukkit.getScheduler().runTaskTimer(plugin, (Runnable) new SaveScheduler(), 60, delay);
                } catch (Exception ex) {
                    sender.sendMessage(ChatColor.RED + "Invalid value. Must be an integer");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid arguments." + ChatColor.GRAY + " /usersapisetinterval <ticks> (e.g., 20 ticks = 1 second)");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to this command.");
        }
        return true;
    }
}

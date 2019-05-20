package me.markiscool.usersapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UsersAPIReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.isOp()) {
            UsersAPIPlugin.getInstance().loadConfig();
            sender.sendMessage(ChatColor.GREEN + "Users.yml has been reloaded.");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to this command.");
        }
        return true;
    }
}

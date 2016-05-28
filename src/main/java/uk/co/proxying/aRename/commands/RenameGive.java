package uk.co.proxying.aRename.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import uk.co.proxying.aRename.utils.Config;
import uk.co.proxying.aRename.utils.CoreUtilities;

/**
 * Created by Kieran Quigley (Proxying) on 28-May-16 for CherryIO.
 */
public class RenameGive implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof ConsoleCommandSender)) {
            if (!(commandSender.hasPermission(new Config<String>("settings.aRename-give-command-permission").getValue()))) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-give-command-permission-fail").getValue()));
                return true;
            }
        }
        if (strings.length == 2) {
            if (Bukkit.getPlayer(strings[0]) == null) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-give-command-player-offline").getValue()));
                return true;
            }
            int amount;
            try {
                amount = Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-give-command-wrong-arguments").getValue()));
                return true;
            }
            if (amount < 0) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-give-command-wrong-arguments").getValue()));
                return true;
            }
            Player player = Bukkit.getPlayer(strings[0]);
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), CoreUtilities.createRenamingItem(amount));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-inventory-full-message").getValue().replace("%amount%", String.valueOf(amount))));
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-inventory-full-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-inventory-full-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-sender-success").getValue().replace("%player%", player.getName()).replace("%amount%", String.valueOf(amount))));
                return true;
            } else {
                player.getInventory().addItem(CoreUtilities.createRenamingItem(amount));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-message").getValue().replace("%amount%", String.valueOf(amount))));
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-give-command-sender-success").getValue().replace("%player%", player.getName()).replace("%amount%", String.valueOf(amount))));
                return true;
            }
        } else {
            commandSender.sendMessage(new Config<String>("messages.error-give-command-wrong-arguments").getValue());
            return true;
        }
    }
}

//rgive proxying 1

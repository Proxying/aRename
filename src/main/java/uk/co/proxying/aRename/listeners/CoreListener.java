package uk.co.proxying.aRename.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.proxying.aRename.Arename;
import uk.co.proxying.aRename.anvilGui.core.AnvilApi;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilGUIInterface;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilSlot;
import uk.co.proxying.aRename.utils.Config;
import uk.co.proxying.aRename.utils.CoreUtilities;

/**
 * Created by Kieran Quigley (Proxying) on 27-May-16 for CherryIO.
 */
public class CoreListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerUseNamingItem(InventoryClickEvent event) {
        if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (!event.getInventory().getName().equalsIgnoreCase("container.crafting")) return;
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) return;
        ItemStack cursor = event.getCursor();
        if (!(CoreUtilities.isRenameItem(cursor))) return;
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (!CoreUtilities.isItemWhitelisted(clickedItem)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-attempted-non-whitelist-rename").getValue()));
            return;
        }
        if (CoreUtilities.isRenameItem(clickedItem)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-cannot-rename-item").getValue()));
            return;
        }
        if (Arename.getInstance().getPlayersRenaming().contains(player.getName())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.error-already-renaming-item").getValue()));
            return;
        }
        event.setCancelled(true);
        event.setCursor(new ItemStack(Material.AIR));
        ItemStack newStack = cursor.clone();
        if (newStack.getAmount() > 1) {
            newStack.setAmount(newStack.getAmount() - 1);
        } else {
            newStack.setType(Material.AIR);
        }
        player.getInventory().addItem(newStack);
        AnvilGUIInterface renameGUI = AnvilApi.createNewGUI(player, anvilClick -> {
            if (anvilClick.getSlot() == AnvilSlot.OUTPUT) {
                if (clickedItem.getItemMeta().hasDisplayName()) {
                    if (anvilClick.getName().equals(clickedItem.getItemMeta().getDisplayName())) {
                        ItemStack clonedItem = cursor.clone();
                        clonedItem.setAmount(1);
                        player.getInventory().addItem(clonedItem);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-rename-disallowed").getValue()));
                        Arename.getInstance().getPlayersRenaming().remove(player.getName());
                        anvilClick.setWillClose(true);
                        anvilClick.setWillDestroy(true);
                        return;
                    }
                }
                if (anvilClick.getName().equals("")) {
                    ItemStack clonedItem = cursor.clone();
                    clonedItem.setAmount(1);
                    player.getInventory().addItem(clonedItem);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-rename-disallowed").getValue()));
                    Arename.getInstance().getPlayersRenaming().remove(player.getName());
                    anvilClick.setWillClose(true);
                    anvilClick.setWillDestroy(true);
                    return;
                }
                if (anvilClick.getName().equalsIgnoreCase(new Config<String>("messages.anvil-rename-prompt").getValue())) {
                    ItemStack clonedItem = cursor.clone();
                    clonedItem.setAmount(1);
                    player.getInventory().addItem(clonedItem);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-rename-disallowed").getValue()));
                    Arename.getInstance().getPlayersRenaming().remove(player.getName());
                    anvilClick.setWillClose(true);
                    anvilClick.setWillDestroy(true);
                    return;
                }
                ItemMeta meta = clickedItem.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', anvilClick.getName()));
                clickedItem.setItemMeta(meta);
                Arename.getInstance().getPlayersRenaming().remove(player.getName());
                anvilClick.setWillClose(true);
                anvilClick.setWillDestroy(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-rename-success").getValue().replace("%itemname%", ChatColor.translateAlternateColorCodes('&', anvilClick.getName()))));
            }
        });
        renameGUI.setSlot(AnvilSlot.INPUT_LEFT, CoreUtilities.editItem(clickedItem.clone(), new Config<String>("messages.anvil-rename-prompt").getValue()));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Arename.getInstance(), renameGUI::open, 2L);
        Arename.getInstance().getPlayersRenaming().add(player.getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void anvilGUIClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.ANVIL) {
            if (Arename.getInstance().getPlayersRenaming().contains(event.getPlayer().getName())) {
                Arename.getInstance().getPlayersRenaming().remove(event.getPlayer().getName());
                event.getPlayer().getInventory().addItem(CoreUtilities.createRenamingItem());
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("messages.completion-rename-cancelled").getValue()));
            }
        }
    }
}

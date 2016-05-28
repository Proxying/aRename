package uk.co.proxying.aRename.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.proxying.aRename.nbtHandler.NBTItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kieran Quigley (Proxying) on 27-May-16 for CherryIO.
 */
public class CoreUtilities {

    public static boolean isRenameItem(ItemStack itemStack) {
        if (itemStack.getType().getId() == new Config<Integer>("settings.aRename-item-id").getValue()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                NBTItem nbtItem = new NBTItem(itemStack);
                if (nbtItem.hasKey(new Config<String>("settings.aRename-item-nbt-identifier").getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isItemBlacklisted(ItemStack itemStack) {
        int itemID = itemStack.getType().getId();
        for (int blackListedID : new Config<List<Integer>>("blacklist.item-rename-blacklist").getValue()) {
            if (itemID == blackListedID) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack editItem(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        itemStack.setAmount(1);
        return itemStack;
    }

    public static ItemStack createRenamingItem(int amount) {
        ItemStack itemStack;
        int itemMeta;
        if (new Config<Integer>("settings.aRename-item-metadata").getValue() != 0) {
            itemMeta = new Config<Integer>("settings.aRename-item-metadata").getValue();
            itemStack = new ItemStack(Material.getMaterial(new Config<Integer>("settings.aRename-item-id").getValue()), amount, (short) itemMeta);
        } else {
            itemStack = new ItemStack(Material.getMaterial(new Config<Integer>("settings.aRename-item-id").getValue()), amount);
        }
        itemStack = CoreUtilities.editItem(itemStack, ChatColor.translateAlternateColorCodes('&', new Config<String>("settings.aRename-item-name").getValue()));
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String parsedLore : new Config<List<String>>("settings.aRename-item-lore").getValue()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', parsedLore));
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean(new Config<String>("settings.aRename-item-nbt-identifier").getValue(), true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
}

package uk.co.proxying.aRename.anvilGui.nms.anvil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface AnvilGUIInterface {
    public Player getPlayer();

    public void setSlot(AnvilSlot var1, ItemStack var2);

    public void open();

    public void destroy();
}


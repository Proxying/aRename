package uk.co.proxying.aRename.anvilGui.nms.v1_9_R2;

import net.minecraft.server.v1_9_R2.ChatMessage;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.proxying.aRename.Arename;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilClickEvent;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilClickEventHandler;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilGUIInterface;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilSlot;

import java.util.HashMap;
import java.util.Map;

class AnvilGUI implements AnvilGUIInterface {
    private Player player;
    private Map<AnvilSlot, ItemStack> items = new HashMap<>();
    private Inventory inv;
    private Listener listener;

    AnvilGUI(final Arename plugin, final Player player, final AnvilClickEventHandler handler) {
        this.player = player;
        this.listener = new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                if (event.getWhoClicked() instanceof Player && event.getInventory().equals(AnvilGUI.this.inv)) {
                    ItemMeta meta;
                    event.setCancelled(true);
                    ItemStack item = event.getCurrentItem();
                    int slot = event.getRawSlot();
                    String name = "";
                    if (item != null && item.hasItemMeta() && (meta = item.getItemMeta()).hasDisplayName()) {
                        name = meta.getDisplayName();
                    }
                    AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.getBySlot(slot), name, plugin, player);
                    handler.onAnvilClick(clickEvent);
                    if (clickEvent.getWillClose()) {
                        event.getWhoClicked().closeInventory();
                    }
                    if (clickEvent.getWillDestroy()) {
                        destroy();
                    }
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                Inventory inv;
                if (event.getPlayer() instanceof Player && (inv = event.getInventory()).equals(AnvilGUI.this.inv)) {
                    inv.clear();
                    destroy();
                }
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                if (event.getPlayer().equals(getPlayer())) {
                    destroy();
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(this.listener, plugin);
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setSlot(AnvilSlot slot, ItemStack item) {
        this.items.put(slot, item);
    }

    @Override
    public void open() {
        EntityPlayer p = ((CraftPlayer) this.player).getHandle();
        AnvilContainer container = new AnvilContainer(p);
        this.inv = container.getBukkitView().getTopInventory();
        for (AnvilSlot slot : this.items.keySet()) {
            this.inv.setItem(slot.getSlot(), this.items.get(slot));
        }
        int c = p.nextContainerCounter();
        p.playerConnection.sendPacket(new PacketPlayOutOpenWindow(c, "minecraft:anvil", new ChatMessage("Repairing"), 0));
        p.activeContainer = container;
        p.activeContainer.windowId = c;
        p.activeContainer.addSlotListener(p);
    }

    @Override
    public void destroy() {
        this.player = null;
        this.items = null;
        HandlerList.unregisterAll(this.listener);
        this.listener = null;
    }

}


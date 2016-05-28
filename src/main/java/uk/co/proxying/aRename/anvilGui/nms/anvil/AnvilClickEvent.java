package uk.co.proxying.aRename.anvilGui.nms.anvil;

import org.bukkit.entity.Player;
import uk.co.proxying.aRename.Arename;

public class AnvilClickEvent {
    private AnvilSlot slot;
    private String name;
    private String playerName;
    private Arename plugin;
    private boolean close = true;
    private boolean destroy = true;

    public AnvilClickEvent(AnvilSlot slot, String name, Arename plugin, Player player) {
        this.slot = slot;
        this.name = name;
        this.plugin = plugin;
        this.playerName = player.getName();
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Arename getPlugin() {
        return this.plugin;
    }

    public AnvilSlot getSlot() {
        return this.slot;
    }

    public String getName() {
        return this.name;
    }

    public boolean getWillClose() {
        return this.close;
    }

    public void setWillClose(boolean close) {
        this.close = close;
    }

    public boolean getWillDestroy() {
        return this.destroy;
    }

    public void setWillDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public void destroy() {
        this.name = null;
        this.playerName = null;
        this.plugin = null;
        this.slot = null;
    }
}


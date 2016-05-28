/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package uk.co.proxying.aRename.anvilGui.core;

import org.bukkit.entity.Player;
import uk.co.proxying.aRename.Arename;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilClickEventHandler;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilGUIInterface;

public class AnvilApi {

    public static AnvilGUIInterface createNewGUI(Player player, AnvilClickEventHandler handler) {
        return Arename.getInstance().getAnvilGUI().createNewGUI(Arename.getInstance(), player, handler);
    }
}


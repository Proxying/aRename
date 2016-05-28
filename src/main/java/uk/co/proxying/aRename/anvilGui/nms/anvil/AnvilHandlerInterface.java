package uk.co.proxying.aRename.anvilGui.nms.anvil;

import org.bukkit.entity.Player;
import uk.co.proxying.aRename.Arename;

public interface AnvilHandlerInterface {
    public AnvilGUIInterface createNewGUI(Arename plugin, Player player, AnvilClickEventHandler eventHandler);
}


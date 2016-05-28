package uk.co.proxying.aRename.anvilGui.nms.v1_8_R3;


import org.bukkit.entity.Player;
import uk.co.proxying.aRename.Arename;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilClickEventHandler;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilGUIInterface;
import uk.co.proxying.aRename.anvilGui.nms.anvil.AnvilHandlerInterface;

public class AnvilHandler implements AnvilHandlerInterface {
    @Override
    public AnvilGUIInterface createNewGUI(Arename plugin, Player player, AnvilClickEventHandler handler) {
        return new AnvilGUI(plugin, player, handler);
    }
}


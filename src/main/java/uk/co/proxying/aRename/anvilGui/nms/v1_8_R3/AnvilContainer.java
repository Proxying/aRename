package uk.co.proxying.aRename.anvilGui.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;

class AnvilContainer extends ContainerAnvil {
    AnvilContainer(EntityHuman entity) {
        super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
    }

    public boolean a(EntityHuman entityhuman) {
        return true;
    }
}


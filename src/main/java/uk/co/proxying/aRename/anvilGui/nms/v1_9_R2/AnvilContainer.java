package uk.co.proxying.aRename.anvilGui.nms.v1_9_R2;


import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.ContainerAnvil;
import net.minecraft.server.v1_9_R2.EntityHuman;

class AnvilContainer extends ContainerAnvil {
    AnvilContainer(EntityHuman entity) {
        super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
    }

    public boolean a(EntityHuman entityhuman) {
        return true;
    }
}


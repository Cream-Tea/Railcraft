/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.util.routing;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface ITileRouting {

    @Nullable
    ItemStack getRoutingTable();

    void setRoutingTable(ItemStack stack);

    boolean isPowered();

}

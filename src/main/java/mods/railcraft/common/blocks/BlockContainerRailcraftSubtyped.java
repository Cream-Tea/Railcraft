/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.blocks;

import com.google.common.collect.BiMap;
import mods.railcraft.api.core.IVariantEnum;
import mods.railcraft.common.blocks.machine.RailcraftBlockMetadata;
import mods.railcraft.common.util.collections.CollectionTools;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by CovertJaguar on 4/13/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public abstract class BlockContainerRailcraftSubtyped<V extends Enum<V> & IVariantEnumBlock<V>> extends BlockContainerRailcraft {
    private RailcraftBlockMetadata annotation;
    private Class<V> variantClass;
    private V[] variantValues;
    private PropertyEnum<V> variantProperty;
    private BiMap<Integer, V> metaMap;

    protected BlockContainerRailcraftSubtyped(Material materialIn) {
        this(materialIn, materialIn.getMaterialMapColor());
    }

    protected BlockContainerRailcraftSubtyped(Material material, MapColor mapColor) {
        super(material, mapColor);
        setup();
    }

    private void setup() {
        if (annotation == null) {
            annotation = getClass().getAnnotation(RailcraftBlockMetadata.class);
            //noinspection unchecked
            variantClass = (Class<V>) annotation.variant();
            variantValues = variantClass.getEnumConstants();
            variantProperty = PropertyEnum.create("variant", variantClass);
            metaMap = CollectionTools.createIndexedLookupTable(variantValues);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, getVariantProperty());
    }

    @SuppressWarnings("unchecked")
    @Override
    public IBlockState getState(@Nullable IVariantEnum variant) {
        if (variant == null)
            return getDefaultState();
        checkVariant(variant);
        return getDefaultState().withProperty(getVariantProperty(), (V) variant);
    }

    @Nonnull
    public final IProperty<V> getVariantProperty() {
        setup();
        return variantProperty;
    }

    public final V getVariant(IBlockState state) {
        return state.getValue(getVariantProperty());
    }

    @Nonnull
    @Override
    public final Class<? extends V> getVariantEnum() {
        return variantClass;
    }

    @Nonnull
    @Override
    public final V[] getVariants() {
        return variantValues;
    }

    @Nonnull
    public final BiMap<Integer, V> getMetaMap() {
        setup();
        return metaMap;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        V[] variants = getVariants();
        if (variants != null) {
            for (V variant : variants) {
                list.add(getStack(variant));
            }
        } else {
            list.add(getStack(null));
        }
    }
}

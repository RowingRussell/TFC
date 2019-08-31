/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.types;

import java.util.function.Predicate;
import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * todo: document API
 */
public class RockCategory extends IForgeRegistryEntry.Impl<RockCategory>
{
    private final float caveGenMod;
    private final float caveFreqMod;

    private final Item.ToolMaterial toolMaterial;
    private final boolean layer1;
    private final boolean layer2;
    private final boolean layer3;
    private final boolean hasAnvil;

    /**
     * A rock category.
     *
     * @param name         The resource location of the rock.
     * @param toolMaterial The tool material used for stone tools made of this rock
     * @param caveGenMod   a modifier for cave generation. Default 0, range -0.5 <> 0.5
     * @param caveFreqMod  another modifier for cave generation. Default 0, sedimentary uses +5
     * @param hasAnvil     if this rock should be able to create a stone anvil
     */
    public RockCategory(@Nonnull ResourceLocation name, @Nonnull Item.ToolMaterial toolMaterial, boolean layer1, boolean layer2, boolean layer3, float caveGenMod, float caveFreqMod, boolean hasAnvil)
    {
        setRegistryName(name);
        this.toolMaterial = toolMaterial;
        this.caveGenMod = caveGenMod;
        this.caveFreqMod = caveFreqMod;
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
        this.hasAnvil = hasAnvil;
    }

    @Nonnull
    public Item.ToolMaterial getToolMaterial()
    {
        return toolMaterial;
    }

    public float getCaveGenMod()
    {
        return caveGenMod;
    }

    public float getCaveFreqMod()
    {
        return caveFreqMod;
    }

    public boolean hasAnvil()
    {
        return hasAnvil;
    }

    @Override
    public String toString()
    {
        //noinspection ConstantConditions
        return getRegistryName().getPath();
    }

    public enum Layer implements Predicate<Rock>
    {
        BOTTOM(3, x -> x.getRockCategory().layer3),
        MIDDLE(2, x -> x.getRockCategory().layer2),
        TOP(1, x -> x.getRockCategory().layer1);

        public final int layer;
        private final Predicate<Rock> filter;

        Layer(int layer, Predicate<Rock> filter)
        {
            this.layer = layer;
            this.filter = filter;
        }

        @Override
        public boolean test(Rock rock)
        {
            return filter.test(rock);
        }
    }
}

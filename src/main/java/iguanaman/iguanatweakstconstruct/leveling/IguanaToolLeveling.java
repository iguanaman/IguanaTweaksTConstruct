package iguanaman.iguanatweakstconstruct.leveling;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import iguanaman.iguanatweakstconstruct.leveling.handlers.LevelingEventHandler;
import iguanaman.iguanatweakstconstruct.leveling.handlers.LevelingToolTipHandler;
import iguanaman.iguanatweakstconstruct.leveling.modifiers.ModMiningLevelBoost;
import iguanaman.iguanatweakstconstruct.leveling.modifiers.ModXpAwareRedstone;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModRedstone;
import tconstruct.tools.TinkerTools;

import java.util.List;
import java.util.ListIterator;

/**
 * The Leveling Pulse. If Leveling were a separate mod instead of pulse-model, this'd be a @Mod
 * This pulse contains all the stuff that has to do with tool leveling.
 */

@Pulse(id = Reference.PULSE_LEVELING, description = "The Iguana Tweaks Leveling System for Tinker's Tools")
public class IguanaToolLeveling {

    private static final int HEAD_Zombie = 2;
    private static final int HEAD_Skeleton = 0;
    private static final int HEAD_Creeper = 4;
    private static final int HEAD_Wither = 1;



    @Handler
    public void init(FMLInitializationEvent event)
    {
        TConstructRegistry.registerActiveToolMod(new LevelingActiveToolMod());

        // substitute modifiers that need an xp-aware implementation
        takeOverModifiers();


        // mobhead modifiers for mining boost
        registerBoostModifiers();
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new LevelingEventHandler());
        MinecraftForge.EVENT_BUS.register(new LevelingToolTipHandler());
    }






    // replace modifiers with our own, adjusted, modifiers
    private void takeOverModifiers()
    {
        List<ItemModifier> mods = ModifyBuilder.instance.itemModifiers;
        for(ListIterator<ItemModifier> iter = mods.listIterator(); iter.hasNext();)
        {
            ItemModifier mod = iter.next();
            // redstone
            if(mod instanceof ModRedstone) {
                iter.set(new ModXpAwareRedstone((ModRedstone) mod));
                Log.trace("Replaced Redstone Modifier");
            }
        }
    }

    private void registerBoostModifiers()
    {
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Zombie), 20, HarvestLevels._2_copper));
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Skeleton), 21, HarvestLevels._3_iron));
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Creeper), 22, HarvestLevels._4_bronze));
        // blaze
        // enderman
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Wither), 25, HarvestLevels._7_ardite));
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(new ItemStack[]{new ItemStack(Items.nether_star)}, 26, HarvestLevels._8_cobalt));

        // rendering code
        ToolCore[] tools = new ToolCore[] { TinkerTools.pickaxe, TinkerTools.hammer };
        int[] modifierIds = new int[] { 20, 21, 22, 24, 25, 26 };
        String[] renderNames = new String[] { "zombiehead", "skeletonskull", "creeperhead", "endermanhead", "witherskeletonskull", "netherstar" };

        for (ToolCore tool : tools)
            for (int index = 0; index < modifierIds.length; ++index)
                TConstructClientRegistry.addEffectRenderMapping(tool, modifierIds[index], Reference.RESOURCE, renderNames[index], true);
    }

    private ItemStack[] getMobHead(int meta)
    {
        return new ItemStack[]{new ItemStack(Items.skull, 1, meta)};
    }
}

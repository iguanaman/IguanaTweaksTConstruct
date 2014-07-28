package iguanaman.iguanatweakstconstruct.leveling;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import iguanaman.iguanatweakstconstruct.leveling.handlers.LevelingEventHandler;
import iguanaman.iguanatweakstconstruct.leveling.handlers.ToolTipHandler;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.HarvestLevels;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.ModifyBuilder;

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

        // mobhead modifiers for mining boost
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Zombie), HarvestLevels._2_copper));
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Skeleton), HarvestLevels._3_iron));
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Creeper), HarvestLevels._4_bronze));
        // blaze
        // enderman
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(getMobHead(HEAD_Wither), HarvestLevels._7_ardite));
        ModifyBuilder.registerModifier(new ModMiningLevelBoost(new ItemStack[]{new ItemStack(Items.nether_star)}, HarvestLevels._8_cobalt));
    }

    private ItemStack[] getMobHead(int meta)
    {
        return new ItemStack[]{new ItemStack(Items.skull, 1, meta)};
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new LevelingEventHandler());
        MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
    }
}

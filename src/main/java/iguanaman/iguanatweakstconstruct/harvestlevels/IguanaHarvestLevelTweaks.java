package iguanaman.iguanatweakstconstruct.harvestlevels;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;

/**
 * The Harvest-Tweaks Pulse. If this were a separate mod instead of pulse-module, it'd be a @Mod
 * This pulse modifies the harvest level of all tools and blocks.
 *
 * A short overview:
 * 0. Wood/Stone
 * 1. Flint/Copper
 * 2. Iron
 * 3. Obsidian
 * 4. Ardite
 * 5. Cobalt
 * 6. Manyullym
 *
 * Check the oreDictlevels to get an idea of what can be harvested with each tier.
 */

@Pulse(id = Reference.PULSE_HARVESTTWEAKS, description = "Modify tool and block mining levels to create a tiered-ish progression")
public class IguanaHarvestLevelTweaks {
    // strength of the tool-material. stone == strength of a stone pick etc.
    public abstract class HarvestLevels {
        public static final int _0_stone = 0;
        public static final int _1_flint = 1;
        public static final int _2_copper = 2;
        public static final int _3_iron = 3;
        public static final int _4_bronze = 4;
        public static final int _5_diamond = 5;
        public static final int _6_obsidian = 6;
        public static final int _7_ardite = 7;
        public static final int _8_cobalt = 8;
        public static final int _9_manyullym = 9;
    }

    @Handler
    public void applyTinkerTweaks(FMLPreInitializationEvent event)
    {
        TinkerToolTweaks.modifyToolMaterials();
    }

    @Handler
    public void applyTweaks(FMLPostInitializationEvent event)
    {
        HarvestLevelTweaks.modifyHarvestLevels();
    }
}

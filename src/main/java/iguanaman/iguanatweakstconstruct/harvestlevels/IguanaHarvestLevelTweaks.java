package iguanaman.iguanatweakstconstruct.harvestlevels;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
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
    // HarvestLevels
    public static String[][] oreDictLevels = {
            {},
            {"Copper", "Coal", "Tetrahedrite", "Aluminum", "Aluminium", "NaturalAluminum", "AluminumBrass", "Shard", "Bauxite", "Zinc"},
            {"Iron", "Pyrite", "Lead", "Silver"},
            {"Tin", "Cassiterite", "Gold", "Lapis", "Steel", "Galena", "Nickel", "Invar", "Electrum", "Sphalerite"},
            {"Diamond", "Emerald", "Redstone", "Ruby", "Sapphire", "Cinnabar", "Quartz",
                    "Obsidian", "CertusQuartz", "Tungstate", "Sodalite", "GreenSapphire", "BlackGranite", "RedGranite"},
            {"Ardite", "Uranium", "Olivine", "Sheldonite", "Osmium", "Platinum"},
            {"Cobalt", "Iridium", "Cooperite", "Titanium"},
            {"Manyullyn"}
    };

    @Handler
    public void applyTweaks(FMLPostInitializationEvent event)
    {

    }
}

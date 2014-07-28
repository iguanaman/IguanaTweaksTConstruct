package iguanaman.iguanatweakstconstruct.harvestlevels;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import iguanaman.iguanatweakstconstruct.harvestlevels.proxy.HarvestCommonProxy;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.common.TProxyCommon;
import tconstruct.tools.ToolProxyCommon;

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
    @SidedProxy(clientSide = "iguanaman.iguanatweakstconstruct.harvestlevels.proxy.HarvestClientProxy", serverSide = "iguanaman.iguanatweakstconstruct.harvestlevels.proxy.HarvestCommonProxy")
    public static HarvestCommonProxy proxy;

    @Handler
    public void applyTinkerTweaks(FMLPreInitializationEvent event)
    {
        TinkerToolTweaks.modifyToolMaterials();

    }

    @Handler
    public void registerHandlers(FMLInitializationEvent event)
    {
        // the only thing this does is replacing GUIs with our own GUIs to display the correct harvest levels
        proxy.initialize();
    }

    @Handler
    public void applyTweaks(FMLPostInitializationEvent event)
    {
        HarvestLevelTweaks.modifyHarvestLevels();
        MinecraftForge.EVENT_BUS.register(new VanillaToolTipHandler());
    }
}

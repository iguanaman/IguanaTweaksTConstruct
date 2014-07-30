package iguanaman.iguanatweakstconstruct.replacing;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import iguanaman.iguanatweakstconstruct.harvestlevels.HarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.harvestlevels.TinkerToolTweaks;
import iguanaman.iguanatweakstconstruct.harvestlevels.VanillaToolTipHandler;
import iguanaman.iguanatweakstconstruct.harvestlevels.proxy.HarvestCommonProxy;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.crafting.ModifyBuilder;

/**
 * Allows the replacement of toolparts.
 * Basically it adds a modifier that turns each toolpart into a modifier.
 */

@Pulse(id = Reference.PULSE_REPLACING, description = "Replace parts of tools")
public class IguanaToolPartReplacing {

    @Handler
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @Handler
    public void init(FMLInitializationEvent event)
    {
        ModifyBuilder.registerModifier(new ModPartReplacement());
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}

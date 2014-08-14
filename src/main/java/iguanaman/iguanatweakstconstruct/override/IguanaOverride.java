package iguanaman.iguanatweakstconstruct.override;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;

import java.io.File;

/**
 * Allows to override:
 * - All material properties
 * - Mining Levels of all tools
 * - Harvest Levels of all oredict Entries
 * - Harvest Levels of all blocks (overrides oredict)
 */
@Pulse(id = Reference.PULSE_OVERRIDE, description = "This module allows to override about any values relevant for TConstruct or IguanaTinkerTweaks.", defaultEnable = false)
public class IguanaOverride {

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        File file = new File(IguanaTweaksTConstruct.configPath, "MaterialOverride.cfg");
        MaterialOverride.doOverride(file);
    }
}

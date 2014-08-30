package iguanaman.iguanatweakstconstruct.override;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.config.Configuration;

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
        doOverride("Material", new MaterialOverride());
        doOverride("Tool", new ToolOverride());
        doOverride("Block", new BlockOverride());
        doOverride("BonusModifier", new ModifierOverride());
        doOverride("HarvestLevelNames", new HarvestLevelNameOverride());
    }

    public static void doOverride(String type, IOverride overrider)
    {
        String configFileName = type + "Override.cfg";
        String defaultFileName = type + "Defaults.cfg";

        Configuration defaultConfig = new Configuration(Reference.configFile(defaultFileName));
        overrider.createDefault(defaultConfig);
        defaultConfig.save();

        Configuration config = new Configuration(Reference.configFile(configFileName));
        config.load();
        overrider.processConfig(config);

        if(config.hasChanged())
            config.save();
    }
}

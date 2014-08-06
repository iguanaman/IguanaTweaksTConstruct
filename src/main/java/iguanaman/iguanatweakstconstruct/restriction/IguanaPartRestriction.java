package iguanaman.iguanatweakstconstruct.restriction;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@Pulse(id = Reference.PULSE_RESTRICTIONS, description = "Various Tweaks for vanilla Minecraft and Tinker's Construct. See Config.")
public class IguanaPartRestriction {
    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        // init the helper
        RestrictionHelper.initPatternParts();

        RestrictionConfig config = new RestrictionConfig();
        config.init(new File(IguanaTweaksTConstruct.configPath, "restrictions.cfg"));

        MinecraftForge.EVENT_BUS.register(new PartRestrictionHandler());
    }
}

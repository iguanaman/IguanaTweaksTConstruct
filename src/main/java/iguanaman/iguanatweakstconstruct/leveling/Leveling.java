package iguanaman.iguanatweakstconstruct.leveling;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import iguanaman.iguanatweakstconstruct.reference.IguanaReference;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.TConstructRegistry;

/**
 * The Leveling Pulse. If Leveling were a separate mod instead of pulse-model, this'd be a @Mod
 * This pulse contains all the stuff that has to do with tool leveling.
 */

@Pulse(id = IguanaReference.PULSE_LEVELING, description = "The Iguana Tweaks Leveling System for Tinker's Tools")
public class Leveling {

    @Handler
    public void init(FMLInitializationEvent event)
    {
        TConstructRegistry.registerActiveToolMod(new LevelingActiveToolMod());
    }

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new LevelingEventHandler());
        MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
    }
}

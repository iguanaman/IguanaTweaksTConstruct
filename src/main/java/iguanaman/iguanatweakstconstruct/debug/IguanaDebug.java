package iguanaman.iguanatweakstconstruct.debug;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraftforge.common.MinecraftForge;

@Pulse(id = "Debug", description = "Stuff used for debugging. You probably don't want this.", defaultEnable = false)
public class IguanaDebug {
    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new DebugTooltipHandler());
    }
}

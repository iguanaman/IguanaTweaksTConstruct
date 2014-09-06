package iguanaman.iguanatweakstconstruct.modcompat.fmp;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import iguanaman.iguanatweakstconstruct.harvestlevels.HarvestLevelTweaks;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.reference.Reference;
import iguanaman.iguanatweakstconstruct.util.Log;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import net.minecraft.item.Item;

import java.lang.reflect.Field;

@Pulse(id = Reference.PULSE_COMPAT_FMP, description = "Makes Saw cut stuff again", pulsesRequired = Reference.PULSE_HARVESTTWEAKS, modsRequired = "ForgeMultipart")
public class IguanaFMPCompat {
    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        for(Object o : Item.itemRegistry)
        {
            // special treatment: FMP saws
            Class clazz = o.getClass();
            if(clazz.getName().equals("codechicken.microblock.ItemSaw"))
            {
                // this is a saw.
                Item item = (Item)o;

                try {
                    Field hlvl = clazz.getDeclaredField("harvestLevel");
                    hlvl.setAccessible(true);
                    hlvl.set(o, HarvestLevelTweaks.getUpdatedHarvestLevel((Integer) hlvl.get(o)));
                    if (Config.logMiningLevelChanges)
                        Log.info(String.format("Changed Cutting Strength for %s to %d", item.getUnlocalizedName(), (Integer) hlvl.get(o)));
                }catch(Exception e)
                {
                    // something failed :(
                    Log.error(String.format("Couldn't update FMP saw %s", item.getUnlocalizedName()));
                }
            }
        }
    }
}

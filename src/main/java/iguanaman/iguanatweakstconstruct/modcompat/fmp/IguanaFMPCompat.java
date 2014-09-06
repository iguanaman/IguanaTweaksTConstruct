package iguanaman.iguanatweakstconstruct.modcompat.fmp;

import codechicken.microblock.ItemSaw;
import codechicken.microblock.Saw;
import cpw.mods.fml.common.SidedProxy;
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
    @SidedProxy(clientSide = "iguanaman.iguanatweakstconstruct.modcompat.fmp.ClientFMPProxy", serverSide = "iguanaman.iguanatweakstconstruct.modcompat.fmp.CommonFMPProxy")
    public static CommonFMPProxy proxy;

    @Handler
    public void postInit(FMLPostInitializationEvent event)
    {
        for(Object o : Item.itemRegistry)
        {
            // is it a saw?
            if(!(o instanceof Saw))
                continue;

            Item item = (Item) o;

            // FMP saw
            if(o instanceof ItemSaw)
            {
                try {
                    Field hlvlField = ItemSaw.class.getDeclaredField("harvestLevel");
                    hlvlField.setAccessible(true);
                    Integer old = (Integer)hlvlField.get(o);
                    Integer hlvl = HarvestLevelTweaks.getUpdatedHarvestLevel(old);

                    // update the value
                    hlvlField.set(o, hlvl);
                    if (Config.logMiningLevelChanges)
                        Log.info(String.format("Changed Cutting Strength for %s from %d to %d", item.getUnlocalizedName(), old, hlvl));
                } catch(Exception e)
                {
                    // something failed :(
                    Log.error(String.format("Couldn't update FMP saw %s", item.getUnlocalizedName()));
                }
            }
        }

        proxy.updateSawRenderers();
    }
}

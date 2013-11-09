package iguanaman.iguanatweakstconstruct.util;

import cpw.mods.fml.common.FMLLog;
import tconstruct.TConstruct;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class IguanaClientEventHandler {


    /* Sounds */
    @ForgeSubscribe
    public void onSound (SoundLoadEvent event)
    {
        try
        {
        	event.manager.addSound("iguanatweakstconstruct:chime.ogg");
            FMLLog.warning("IguanaTweaksTConstruct: Loaded chime sound");
        }
        catch (Exception e)
        {
            FMLLog.warning("IguanaTweaksTConstruct: Failed to load chime sound");
        }
    }

}

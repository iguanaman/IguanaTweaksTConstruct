package iguanaman.iguanatweakstconstruct.old;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;

public class IguanaClientEventHandler {


	/* Sounds */
	@SubscribeEvent
	public void onSound (SoundLoadEvent event)
	{
		try
		{
			//event.manager.addSound("iguanatweakstconstruct:chime.ogg");
			FMLLog.warning("IguanaTweaksTConstruct: Loaded chime sound");
		}
		catch (Exception e)
		{
			FMLLog.warning("IguanaTweaksTConstruct: Failed to load chime sound");
		}
	}

}

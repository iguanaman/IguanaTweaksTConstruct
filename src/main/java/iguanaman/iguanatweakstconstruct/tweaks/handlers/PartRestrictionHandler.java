package iguanaman.iguanatweakstconstruct.tweaks.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import tconstruct.library.event.PartBuilderEvent;

public class PartRestrictionHandler {
    @SubscribeEvent
    public void onPartBuilding(PartBuilderEvent event)
    {
        if(event.pattern != null)
            Log.info(event.pattern.getDisplayName());
        if(event.otherPattern != null)
            Log.info(event.otherPattern.getDisplayName());
    }
}

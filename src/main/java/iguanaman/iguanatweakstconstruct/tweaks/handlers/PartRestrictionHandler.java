package iguanaman.iguanatweakstconstruct.tweaks.handlers;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.util.Log;
import iguanaman.iguanatweakstconstruct.util.RestrictionHelper;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.event.PartBuilderEvent;

import java.util.Set;

public class PartRestrictionHandler {
    @SubscribeEvent
    public void onPartBuilding(PartBuilderEvent.NormalPart event)
    {
        PatternBuilder.ItemKey key = PatternBuilder.instance.getItemKey(event.material);
        if(key == null)
            return;
        PatternBuilder.MaterialSet set = ((PatternBuilder.MaterialSet)PatternBuilder.instance.materialSets.get(key.key));
        if(set == null)
            return;

        int matID = set.materialID;
        // get the part that should be built
        //ItemStack part = PatternBuilder.instance.getMatchingPattern(event.pattern, event.material, set);

        // find matching entry for the toolpart
        String name = RestrictionHelper.getPatternName(event.pattern);
        if(name == null || name.isEmpty())
            return;

        // check if it's restricted
        Set<Integer> restrictedMats = Config.restrictedParts.get(name); // guaranteed to exist
        if(restrictedMats.contains(matID))
            event.setResult(Event.Result.DENY);
    }
}

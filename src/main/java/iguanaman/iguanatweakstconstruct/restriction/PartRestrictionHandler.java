package iguanaman.iguanatweakstconstruct.restriction;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.Config;
import iguanaman.iguanatweakstconstruct.restriction.RestrictionHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.event.PartBuilderEvent;
import tconstruct.library.util.IToolPart;

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
        Set<Integer> restrictedMats = RestrictionConfig.restrictedParts.get(name); // guaranteed to exist
        if(restrictedMats.contains(matID))
            event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if (event.entityPlayer == null)
            return;

        // we're only interested if it's a tool part
        if (!(event.itemStack.getItem() instanceof IToolPart))
            return;

        // is restricted?
        ItemStack stack = event.itemStack;
        IToolPart part = (IToolPart)stack.getItem();

    }
}

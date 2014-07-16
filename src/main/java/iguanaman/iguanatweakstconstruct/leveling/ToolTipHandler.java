package iguanaman.iguanatweakstconstruct.leveling;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.IguanaConfig;
import iguanaman.iguanatweakstconstruct.util.IguanaLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Pickaxe;
import tconstruct.library.tools.ToolCore;

import java.util.List;
import java.util.ListIterator;

public class ToolTipHandler {
    // the prefix used for "+ X attack damage". Thanks Tic Tooltips ;)
    private static String plusPrefix = "\u00A79+";

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        if(event.entityPlayer == null)
            return;

        // we're only interested in tinker tools, obviously
        if(!(event.itemStack.getItem() instanceof ToolCore))
            return;

        ItemStack stack = event.itemStack;
        ListIterator<String> inserter = findInsertSpot(event.toolTip);

        // find spot to insert

        ToolCore tool = (ToolCore)event.itemStack.getItem();
        NBTTagCompound tags = stack.getTagCompound().getCompoundTag(tool.getBaseTagName()); // tinker tags
        boolean hasMiningLevel = tool instanceof Pickaxe || tool instanceof Hammer;

        // add mining level if applicable
        if(hasMiningLevel && tags.hasKey(IguanaLevelingLogic.TAG_BOOST_EXP))
        {
            int hLevel = tags.getInteger("HarvestLevel");
            inserter.add(IguanaLevelingTooltips.getMiningLevelTooltip(hLevel));
            // mining level boost progress
            if(IguanaConfig.levelingPickaxeBoost && IguanaConfig.showTooltipXP)
            {
                if(tags.hasKey(IguanaLevelingLogic.TAG_IS_BOOSTED))
                    inserter.add(IguanaLevelingTooltips.getBoostedTooltip());
                else
                    inserter.add(IguanaLevelingTooltips.getXpToolTip(stack, tags, true));
            }
        }

        // add skill level
        int level = tags.getInteger(IguanaLevelingLogic.TAG_LEVEL);
        inserter.add(IguanaLevelingTooltips.getLevelTooltip(level));

        // skill level progress
        if(IguanaConfig.showTooltipXP)
            inserter.add((IguanaLevelingTooltips.getXpToolTip(stack, tags, false)));

        // since we added at least one line we'll add an empty spacing line at the end
        inserter.add("");
    }

    private ListIterator<String> findInsertSpot(List<String> tooltip)
    {
        ListIterator<String> iterator = tooltip.listIterator();

        // progress to the end, check if there's a "+ damage" stuff
        while(iterator.hasNext())
            if(iterator.next().startsWith(plusPrefix))
            {
                iterator.previous();
                break;
            }

        // we're either directly before the "+ damage" or at the end now

        return iterator;
    }
}

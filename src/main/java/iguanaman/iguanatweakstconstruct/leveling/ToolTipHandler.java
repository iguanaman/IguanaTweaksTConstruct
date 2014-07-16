package iguanaman.iguanatweakstconstruct.leveling;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import iguanaman.iguanatweakstconstruct.reference.IguanaConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;
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
        // find spot to insert our tooltip data
        ListIterator<String> inserter = findInsertSpot(event.toolTip);
        // does the user hold shift?
        boolean advanced = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
        // only allow advanced (xp) tooltip if config option is set
        advanced &= IguanaConfig.showTooltipXP;


        ToolCore tool = (ToolCore)event.itemStack.getItem();
        NBTTagCompound tags = stack.getTagCompound().getCompoundTag(tool.getBaseTagName()); // tinker tags
        boolean hasMiningLevel = tool instanceof Pickaxe || tool instanceof Hammer;

        // add mining level if applicable
        if(hasMiningLevel && tags.hasKey(IguanaLevelingLogic.TAG_BOOST_EXP))
        {
            int hLevel = tags.getInteger("HarvestLevel");
            String mLvl = IguanaLevelingTooltips.getMiningLevelTooltip(hLevel);
            // is the pick applicable for mining level boosting? if yes display xp
            if(IguanaLevelingLogic.canBoostMiningLevel(hLevel)) {
                // add minimal xp if config option is set
                if (!advanced && IguanaConfig.showMinimalTooltipXP && !IguanaLevelingLogic.isBoosted(tags))
                    mLvl += " (" + IguanaLevelingTooltips.getBoostXpString(stack, tags, false) + ")";
                inserter.add(mLvl);

                // advanced mining level boost progress info
                if (advanced && IguanaConfig.levelingPickaxeBoost && IguanaConfig.showTooltipXP) {
                    if (IguanaLevelingLogic.isBoosted(tags))
                        inserter.add(IguanaLevelingTooltips.getBoostedTooltip());
                    else
                        inserter.add(IguanaLevelingTooltips.getBoostXpToolTip(stack, tags));
                }
            }
        }

        // add skill level
        int level = IguanaLevelingLogic.getLevel(tags);
        String lvl = IguanaLevelingTooltips.getLevelTooltip(level);
        if(!advanced && IguanaConfig.showMinimalTooltipXP && !IguanaLevelingLogic.isMaxLevel(tags))
            lvl += " (" + IguanaLevelingTooltips.getXpString(stack, tags, false) + ")";
        inserter.add(lvl);

        // skill level progress
        if(advanced && IguanaConfig.showTooltipXP && !IguanaLevelingLogic.isMaxLevel(tags))
            inserter.add(IguanaLevelingTooltips.getXpToolTip(stack, tags));

        // since we added at least one line we'll add an empty spacing line at the end
        inserter.add("");

        // add info that you can hold shift for more details
        if(!advanced && IguanaConfig.showTooltipXP)
            event.toolTip.add(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.ITALIC.toString() + "Hold SHIFT for XP");
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
